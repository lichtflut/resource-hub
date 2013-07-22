require 'vcard'
require 'securerandom'
require 'rdf'
require 'rdf/ntriples'
require 'rdf/n3'

input           =  ARGV[0]
out             =  ARGV[1]
company         =  ARGV[2]
employee_prefix =  ARGV[3]

def rid
  RDF::URI.new('local:' + SecureRandom.uuid)
end

def uri(s)
  RDF::URI::new s
end

def uri_common(s)
  uri 'http://rb.lichtflut.de/common#' + s
end

def uri_from_email(namespace, m)
  if !namespace || !m 
    return rid
  end
  parts = m.split '@'
  uri namespace + parts[1] + '/' + parts[0] 
end

ENTITY = uri 'http://rb.lichtflut.de/system#Entity'
PERSON = uri_common 'Person'
CONTACT_DATA = uri_common 'ContactData'
COMPANY_URI = uri company if company
EMPLOYEE_NAMESPACE = employee_prefix

# BEGIN

puts "Reading VCards from #{input}."

graph = RDF::Graph.new 
vcards = Vcard::Vcard.decode File.read(input)  

puts "Read #{vcards.size} VCards."

vcards.each do |v|
  id = uri_from_email(EMPLOYEE_NAMESPACE, v.emails.first)
  graph << [id, RDF.type, PERSON] 
  graph << [id, RDF.type, ENTITY]  
  graph << [id, RDF::RDFS.label, v.name.fullname] 
  graph << [id, uri_common('hasFirstName'), v.name.given]  
  graph << [id, uri_common('hasLastName'), v.name.family]
  v.emails.each do |e|
    graph << [id, uri_common('hasEmailAddress'), e.to_s]  
  end
  v.telephones.each do |t|
     contact_id = RDF::URI.new('local:' + SecureRandom.uuid)     
     graph << [id, uri_common('hasContactData'), contact_id]  
     graph << [contact_id, RDF.type, CONTACT_DATA]  
     graph << [contact_id, RDF::RDFS.label, t.to_s] 
     graph << [contact_id, uri_common('hasCategory'), uri_common('MobilePhoneNumber')]  
     graph << [contact_id, uri_common('hasValue'), t.to_s]  
  end
  if (COMPANY_URI)
      graph << [id, uri_common('isEmployedBy'), COMPANY_URI] 
  end
end

puts "Writing persons to #{out}."
File.open(out, 'w') { |file| file.write(graph.dump(:n3)) }






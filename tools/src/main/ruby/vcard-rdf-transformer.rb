require 'vcard'
require 'securerandom'
require 'rdf'
require 'rdf/ntriples'
require 'rdf/n3'

def uri(s)
  RDF::URI::new s
end

def uri_common(s)
  uri 'http://rb.lichtflut.de/common#' + s
end

ENTITY = uri 'http://rb.lichtflut.de/system#Entity'
PERSON = uri_common 'Person'

# BEGIN

input = ARGV[0]
out = ARGV[1]
puts "Reading VCards from #{input}."



graph = RDF::Graph.new 
vcards = Vcard::Vcard.decode File.read(input)  

puts "Read #{vcards.size} VCards."

vcards.each do |v|
  id = RDF::URI.new('local:' + SecureRandom.uuid)
  graph << [id, RDF.type, PERSON] 
  graph << [id, RDF.type, ENTITY]  
  graph << [id, RDF::RDFS.label, v.name.fullname] 
  graph << [id, uri_common('hasFirstName'), v.name.given]  
  graph << [id, uri_common('hasLastName'), v.name.family]
  v.emails.each do |e|
    graph << [id, uri_common('hasEmailAddress'), e.to_s]  
  end
  v.telephones.each do |t|
    # TODO: create contact data
  end
end

puts "Writing persons to #{out}."
File.open(out, 'w') { |file| file.write(graph.dump(:n3)) }






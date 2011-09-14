package de.lichtflut.rb.core.api.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.model.ElementaryDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.api.SchemaImporter;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.parser.AbstractRSParsingUnit;
import de.lichtflut.rb.core.schema.parser.RSErrorLevel;
import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultErrorReporter;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultImpl;
import de.lichtflut.rb.core.schema.persistence.RBSchemaStore;
import de.lichtflut.rb.core.spi.IRBServiceProvider;

/**
 *.
 * Created: Sep 14, 2011
 *
 * @author Raphael Esterle
 */

abstract class AbstractSchemaImporter implements SchemaImporter{

	// -------------MEMBER-FIELDS--------------------------

	private ArastrejuGate gate = null;
	private RBSchemaStore store = null;
	private RSFormat format;

	//logger
	private final Logger logger = LoggerFactory.getLogger(RSParsingResultErrorReporter.class);


	/**
	 * Constructor.
	 * @param provider -
	 */
	public AbstractSchemaImporter(final IRBServiceProvider provider) {
		//Trigger a NullPointerException
		this.gate = provider.getArastejuGateInstance();
		store = new RBSchemaStore(this.gate);
	}


	/**
	 * @return {@link RSFormat}
	 */
	abstract RSFormat getFormat();

	/**
	 * @return result -
	 * @param is -
	 */
	public RSParsingResult generateSchemaModelThrough(final InputStream is){
		RSParsingResultImpl result = new RSParsingResultImpl();
		AbstractRSParsingUnit pUnit = getFormat().getParsingUnit();
		pUnit.setErrorReporter(new RSParsingResultErrorReporter(result));
		Collection<ResourceSchemaType> resultTypes;
		try {
			resultTypes = pUnit.parse(is);
			result.merge(convertToParsingResult(resultTypes));
		} catch (de.lichtflut.rb.core.schema.parser.exception.RSMissingErrorReporterException e) {
			logger.error("An error-reporter is not set", e);
			result.addErrorMessage(e.getMessage(),RSErrorLevel.SYSTEM);
		}
		return result;
	}

	// -----------------------------------------------------

	/**
	 * @param file -
	 * @return result -
	 */
	public RSParsingResult generateSchemaModelThrough(final File file){
		try {
			return generateSchemaModelThrough(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			RSParsingResultImpl result = new RSParsingResultImpl();
			logger.info("File " + file.getAbsolutePath() + " was not found");
			result.addErrorMessage("File " + file.getAbsolutePath() + " was not found");
			return result;
		}
	}

	// -----------------------------------------------------

	/**
	 * @param s -
	 * @return {@link RSParsingResult}
	 */
	public RSParsingResult generateSchemaModelThrough(final String s){
		return generateSchemaModelThrough(new ByteArrayInputStream(s.getBytes()));
	}

	// -----------------------------------------------------

	/**
	 * @param is -
	 * @return {@link RSParsingResult}
	 */
	public RSParsingResult generateAndResolveSchemaModelThrough(final InputStream is){
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.merge(generateSchemaModelThrough(is));
		result.setErrorLevel(RSErrorLevel.INTERPRETER);

		//Load all persisted Properties
		Collection<PropertyDeclaration> persistedPDecs = store.loadAllPropertyDeclarations(null);
		Collection<PropertyDeclaration> globalPDecs = result.getPropertyDeclarations();
		//build hashMaps for those to get some better access functionalities
		HashMap<String, PropertyDeclaration> persistedPDecsHash = new HashMap<String, PropertyDeclaration>();
		HashMap<String, PropertyDeclaration> globalPDecsHash = new HashMap<String, PropertyDeclaration>();
		//TODO: Improve this, some points to optimize
		//Fill those hashmaps
		for (PropertyDeclaration pDec : persistedPDecs){persistedPDecsHash.put(pDec.getIdentifierString(),pDec);}
		for (PropertyDeclaration pDec : globalPDecs){ globalPDecsHash.put(pDec.getIdentifierString(),pDec);}

		//Merge them together, the newer global ones will override the older persisted ones
		for (PropertyDeclaration pDec : globalPDecsHash.values()) {
			persistedPDecsHash.put(pDec.getIdentifierString(), pDec);
		}

		//Iterate over assertions:
		for (ResourceSchema rSchema : result.getResourceSchemas()) {
			for (PropertyAssertion assertion : rSchema.getPropertyAssertions()) {
				PropertyDeclaration assertionPDec = assertion.getPropertyDeclaration();
				if(assertionPDec==null){
					//Get the pDec from given identifier
					assertionPDec = persistedPDecsHash.get(assertion.getPropertyIdentifier());
					//if assertionPDec is still null
					result.addErrorMessage("Wasnt able to resolve PropertyDeclaration for "
							+ assertionPDec.getIdentifierString());
					continue;
				}
				PropertyDeclaration persistedPDec =
					persistedPDecsHash.get(assertion.getPropertyDeclaration().getIdentifierString());
				//This must be a new or inherited property
				if(persistedPDec == null){
					//check if it's inhertied
					persistedPDec = persistedPDecsHash.get(assertion.getPropertyIdentifier());
					//if persistedPDec is still null, it must be a new property
					if(persistedPDec != null){
						//Resolve and merge assertion
						mergePropertyDecs(assertionPDec,persistedPDec);
					}
				}else{
					mergePropertyDecs(assertionPDec,persistedPDec);
				}
				persistedPDecsHash.put(assertionPDec.getIdentifierString(), assertionPDec);
			}//End of Inner for
		}

		result.setPropertyDeclarations(persistedPDecsHash.values());

		if(result.isErrorOccured()){
			return result;
		}
		//Otherwise, do sth.?
		return result;
	}

	// -----------------------------------------------------

	/**
	 * <p>
	 * Merges two {@link PropertyDeclarations} the newOne has a higher priority.
	 * </p>
	 * @param newOne -
	 * @param oldOne -
	 */
	private void mergePropertyDecs(final PropertyDeclaration newOne, final PropertyDeclaration oldOne){
		newOne.getConstraints().addAll(oldOne.getConstraints());
		System.out.println("new->"+newOne+" old-->"+oldOne);
		/* TODO: This is not correct.
		 * Due to the fact that the default value is STRING instead of UNDEFINED, we have to expand this selection
		 * This would make it impossible to overwrite a global value with a value of type string, please fix it
		 */
		if(((newOne.getElementaryDataType()==ElementaryDataType.UNDEFINED))
				||(newOne.getElementaryDataType()==ElementaryDataType.STRING)){
			newOne.setElementaryDataType(oldOne.getElementaryDataType());
		}
	}

	/**
	 * @param f -
	 * @return {@link RSParsingResult}
	 */
	public RSParsingResult generateAndResolveSchemaModelThrough(final File f) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		try {
			result.merge(generateAndResolveSchemaModelThrough(new FileInputStream(f)));
		} catch (FileNotFoundException e) {
			logger.info("Schema-File " + f.getAbsolutePath() + " was not found");
			result.addErrorMessage("File " + f.getAbsolutePath() + " was not found", RSErrorLevel.SYSTEM);
		}
		return result;
	}

	/**
	 * @param s -
	 * @return {@link RSParsingResult}
	 */
	public RSParsingResult generateAndResolveSchemaModelThrough(final String s) {
		return generateAndResolveSchemaModelThrough(new ByteArrayInputStream(s.getBytes()));
	}


	/**
	 * <p>
	 * This method converts a {@link Collection} of {@link ResourceSchemaType} types to a {@link RSParsingResult}.
	 * </p>
	 * @param types - the given type-collection
	 * @return the parsing result
	 */
	private RSParsingResult convertToParsingResult(final Collection<ResourceSchemaType> types){
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.setErrorLevel(RSErrorLevel.INTERPRETER);
		if(types==null){ return result;}
		for (ResourceSchemaType type : types) {
			if(type instanceof ResourceSchema){result.addResourceSchema((ResourceSchema) type);}
			if(type instanceof PropertyDeclaration){result.addPropertyDeclaration((PropertyDeclaration) type);}
		}
		return result;
	}

	// -----------------------------------------------------

	/**
	 * @param representation -
	 * @param format -
	 */
	public void storeSchemaRepresentation(final String representation, final RSFormat format) {
		this.store.storeSchemaRepresentation(representation, format);
	}

}

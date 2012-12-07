package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;

/**
 * <p>
 *  Additional information about the visualization of an RB field.
 * </p>
 *
 * <p>
 *  Created 10.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public interface VisualizationInfo extends Serializable{

	public static final VisualizationInfo DEFAULT = new VisualizationInfo() {
		@Override
		public boolean isEmbedded() {
			return false;
		}
		@Override
		public boolean isFloating() {
			return false;
		}
		@Override
		public String getStyle() {
			return "";
		}
	};

	// ----------------------------------------------------

	/**
	 * Resource references may be embedded.
	 * @return True if the referenced resources shall be embedded.
	 */
	boolean isEmbedded();

	/**
	 * Flag indicating if the field shall float or if it shall break. Floating fields will be
	 * displayed directly behind the preceding field, if possible. Breaking fields will always start
	 * a new line.
	 * <strong>This flag is only regarded for embedded fields!</strong>
	 * @return true if floating, false if breaking.
	 */
	boolean isFloating();

	/**
	 * Get additional CSS styling info for this field.
	 * @return The styling info.
	 */
	String getStyle();



}

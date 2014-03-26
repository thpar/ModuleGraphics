package be.ugent.psb.modulegraphics.elements;

import java.awt.Color;
import java.lang.reflect.Field;

/**
 * Utility class to work with colors.
 * 
 * @author thpar
 * 
 */
public class ColorFactory {

    

    /**
     * First tries to convert the String into an integer. If this works, it's assumed that
     * this encodes the RGB value.
     * 
     * If this throws an error: 
     * Takes a color String in the format of RGB(FFFFFF), #FFFFFF or a common color name
     * (red, blue, gray, black, ...) (case insensitive) and returns the matching
     * <code>Color</code>.
     * 
     * @param colorString
     *            string with RGB value or textual color. Will always return a
     *            color. If no matching color is found, returns gray.
     * @return the matching color object.
     */
	public static Color decodeColor(String colorString) {
		Color color;
		try {
			try{
				int colorInt = Integer.valueOf(colorString);
				return new Color(colorInt);
			} catch (NumberFormatException e){
				if (colorString.startsWith("#")) {
					color = Color.decode(colorString);
				} else if (colorString.startsWith("RGB")) {
					// extract RGB value and create Color object
					String rgb = colorString.substring(4, 10);
					color = Color.decode("#" + rgb);
				} else {
					// if a color name is given, see if a Color constant is
					// defined, otherwise use gray.
					Field colorField = Color.class.getDeclaredField(colorString);
					color = (Color) colorField.get(Color.class);
				}
			}
		} catch (Exception e) {
			// the method can fail is the text representation has no matching
			// color constant of if the RGB value isn't hexa-decimal, decimal or
			// octal.
			// in that case: be gray.
			color = Color.GRAY;
		}
		return color;
    }

    /**
     * Decide in which color to shade a figure.
     * 
     * @param shapeColor
     * @return
     */
    public static Color getShadeColor(Color shapeColor) {
        // TODO better measurement for darkness?
        if (shapeColor == Color.BLUE) {
            return Color.CYAN;
        } else if (shapeColor.getBlue() < 20 && shapeColor.getRed() < 50 && shapeColor.getGreen() < 50
                || (shapeColor.getBlue() > 180 && (shapeColor.getRed() < 100 && shapeColor.getGreen() < 100))) {
            return shapeColor.brighter().brighter().brighter().brighter().brighter().brighter();
        } else {
            return shapeColor.darker();
        }
    }

    
    /**
     * Decide in which color to put text on a coloured figure.
     * 
     * @param shapeColor
     * @return
     */
    public static Color getTextColor(Color shapeColor) {
        if (shapeColor == Color.BLUE) {
            return Color.WHITE;
        } else if (shapeColor.getBlue() < 20 && shapeColor.getRed() < 50 && shapeColor.getGreen() < 50
                || (shapeColor.getBlue() > 180 && (shapeColor.getRed() < 100 && shapeColor.getGreen() < 100))) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }
    
    /**
     * Takes a color and returns the hexadecimal representation in the format
     * that can be re-decoded with Color.decode(#xxxxxx)
     * 
     * @param c
     * @return
     */
    public static String toColorHex (Color c)	{
		String s = Integer.toHexString( c.getRGB() & 0xffffff );
		if ( s.length() < 6 ) { 
			// pad on left with zeros
			s = "000000".substring( 0, 6 - s.length() ) + s;
		}
		return '#' + s;
	}

   
}

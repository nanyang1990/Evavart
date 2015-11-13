package gui.animate.cellanimate;

import java.awt.Color;

public class Palette
{
  int lastIndex;
  double[] rangeStart;
  double[] rangeEnd;
  Drawable[] drawables;

  public Palette()
  {
  lastIndex = 0;
  rangeStart = new double[1];
  rangeEnd = new double[1];
  drawables = new Drawable[1];
  rangeStart[0] = 0;
  rangeEnd[0] = 1;
  drawables[0] = new PlainColor(Color.white);
  }

/**
 * Method getDrawable.
 * @param value
 * @return Drawable
 */
  Drawable getDrawable(double value)
  {
    for (int i=0; i<=lastIndex; i++)
    {
      if (value >= rangeStart[i] && value < rangeEnd[i])
      {
        return (drawables[i]);
      }
    }
    //if there is no match, it returns the default PlainColor
    return (new PlainColor());
  }

/**
 * Method addLine.
 * @param from
 * @param to
 * @param drawable
 */
  protected void addLine(double from, double to, Drawable drawable)
  {
    lastIndex = lastIndex + 1;
    double[] rStart = new double[lastIndex+1];
    double[] rEnd = new double[lastIndex+1];
    Drawable[] drbles = new Drawable[lastIndex+1];
    for (int i=0; i<lastIndex; i++)
    {
      rStart[i] = rangeStart[i];
      rEnd[i] = rangeEnd[i];
      drbles[i] = drawables[i];
    }
    rStart[lastIndex] = from;
    rEnd[lastIndex] = to;
    drbles[lastIndex] = drawable;

    rangeStart = rStart;
    rangeEnd = rEnd;
    drawables = drbles;
  }

/**
 * Method removeLine.
 * @param index
 */
  protected void removeLine(int index)
  {
    if (index >=0 && index <= lastIndex)
    {
      lastIndex = lastIndex - 1;
      double[] rStart = new double[lastIndex+1];
      double[] rEnd = new double[lastIndex+1];
      Drawable[] drbles = new Drawable[lastIndex+1];
      for (int i=0; i<index; i++)
      {
        rStart[i] = rangeStart[i];
        rEnd[i] = rangeEnd[i];
        drbles[i] = drawables[i];
      }
      for (int i=index+1; i<=lastIndex; i++)
      {
        rStart[i-1] = rangeStart[i];
        rEnd[i-1] = rangeEnd[i];
        drbles[i-1] = drawables[i];
      }
      rangeStart = rStart;
      rangeEnd = rEnd;
      drawables = drbles;
    }
  }

/**
 * Method toTable.
 * @return Object[][]
 * Returns the content of the Palette for loading into
 * a table
 */
  protected Object[][] toTable()
  {
    Object[][] data = new Object[lastIndex+1][3];
    for (int i=0; i<=lastIndex; i++)
    {
      data[i][0] = new Double(rangeStart[i]);
      data[i][1] = new Double(rangeEnd[i]);
      Color colorValue = ( drawables[i] instanceof PlainColor) ? ((PlainColor)drawables[i]).getColor() :null;
      String textureValue = ( drawables[i] instanceof Texture) ? ((Texture)drawables[i]).getFileName() :null;
      if (colorValue != null)
      {
        data[i][2] = colorValue;
      }
      else
      {
        data[i][2] = textureValue;
      }
    }
    return (data);
  }

/**
 * Method fromTable.
 * @param data
 * Builds a palette from the data on the table
 */
  protected void fromTable(Object[][] data)
  {
    lastIndex = data.length - 1;

    rangeStart = new double[lastIndex+1];
    rangeEnd = new double[lastIndex+1];
    drawables = new Drawable[lastIndex+1];

    for (int i=0; i<=lastIndex; i++)
    {
      rangeStart[i] = ((Double) data[i][0]).doubleValue();
      rangeEnd[i] = ((Double) data[i][1]).doubleValue();

      Color colorValue = ( data[i][2] instanceof Color) ? ((Color)data[i][2]) :null;
      String textureValue = ( data[i][2] instanceof String) ? ((String)data[i][2]) :null;
      if (colorValue != null)
      {
        drawables[i] = new PlainColor(colorValue);
      }
      else
      {
        drawables[i] = new Texture(textureValue);
      }
    }
  }

}
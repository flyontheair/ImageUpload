package com.springapp.mvc.image;

public class ImageSize {

    public final int width;
    public final int height;
    public final String size;
    public ImageSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.size = width + "x" + height;
    }
    
    public ImageSize(String size) {
        this.size = size;
        String[] split = size.split("x");
        width = Integer.parseInt(split[0]);
        height = Integer.parseInt(split[1]);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        result = prime * result + width;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ImageSize other = (ImageSize) obj;
        if (height != other.height)
            return false;
        if (size == null) {
            if (other.size != null)
                return false;
        } else if (!size.equals(other.size))
            return false;
        if (width != other.width)
            return false;
        return true;
    }

}

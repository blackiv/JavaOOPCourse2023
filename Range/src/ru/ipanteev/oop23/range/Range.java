package ru.ipanteev.oop23.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double point) {
        return point >= from && point <= to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Range range = (Range) o;
        return range.from == from && range.to == to;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Double.hashCode(from);
        hash = prime * hash + Double.hashCode(to);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("(%.2f; %.2f)", from, to);
    }

    public Range getIntersection(Range range) {
        if (range.to <= from || range.from >= to) {
            return null;
        }

        return new Range(Math.max(from, range.from), Math.min(to, range.to));
    }

    public Range[] getUnion(Range range) {
        if (range.to < from || range.from > to) {
            return new Range[]{new Range(from, to), new Range(range.from, range.to)};
        }

        return new Range[]{new Range(Math.min(from, range.from), Math.max(to, range.to))};
    }

    public Range[] getDifference(Range range) {
        //пересечения нет, возвращаем this
        //range полностью помещается в this - возвращаем 2 интервала
        //частично перекрывает this - 1 интервал
        //range полностью перекрывает this - нечего возвращать

        if (range.to <= from || range.from >= to) {
            return new Range[]{new Range(from, to)};
        }

        if (isInside(range.from)) {
            if (isInside(range.to)) {
                return new Range[]{new Range(from, range.from), new Range(range.to, to)};
            } else {
                return new Range[]{new Range(from, range.from)};
            }
        } else {
            if (isInside(range.to)) {
                return new Range[]{new Range(range.to, to)};
            } else {
                return new Range[]{};
            }
        }
    }
}

package ru.oop23.range;

import java.util.Objects;

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
        return point > from && point < to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Range range)) return false;
        return Double.compare(range.from, this.from) == 0 && Double.compare(range.to, this.to) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrom(), getTo());
    }

    @Override
    public String toString() {
        return String.format("[%.2f;%.2f]", from, to);
    }

    public Range getIntersectionRange(Range range) {
        if (range.to < this.from || range.from > this.to) {
            return null;
        }

        double newRangeFrom = Math.max(this.from, range.from);
        double newRangeTo = Math.min(this.to, range.to);

        return new Range(newRangeFrom, newRangeTo);
    }

    public Range[] getUnionRanges(Range range) {
        if (range.to < this.from || range.from > this.to) {
            return new Range[]{this, range};
        }

        double newRangeFrom = Math.min(this.from, range.from);
        double newRangeTo = Math.max(this.to, range.to);

        return new Range[]{new Range(newRangeFrom, newRangeTo)};
    }

    public Range[] getDifferentRanges(Range range) {
        Range intersecitonRange = getIntersectionRange(range);
        //если пусто, то пересечения нет, возвращаем this
        //если совпадает с this, то интервал range полностью перекрывает этот - нечего возвращать
        //если совпадает с range, то интервал range полностью помещается в this - возвращаем 2 интервала
        //в остальных случаях 1 интервал

        if (intersecitonRange == null) {
            return new Range[]{this};
        }

        if (intersecitonRange.equals(this)) {
            return new Range[]{};
        }

        if (intersecitonRange.equals(range)) {
            return new Range[]{new Range(this.from, range.from), new Range(range.to, this.to)};
        }

        Range differentRange;

        if (intersecitonRange.from == this.from) {
            differentRange = new Range(intersecitonRange.to, this.to);
        } else {
            differentRange = new Range(this.from, intersecitonRange.from);
        }

        return new Range[]{differentRange};
    }
}

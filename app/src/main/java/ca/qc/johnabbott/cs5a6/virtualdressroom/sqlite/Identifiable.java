package ca.qc.johnabbott.cs5a6.virtualdressroom.sqlite;




/**
 * Indicated model classes that have an ID field.
 *
 * @param <I>
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public interface Identifiable<I> {
    I getId();
    Identifiable<I> setId(I id);


}

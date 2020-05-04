package ro.ubb.movie_catalog.core.domain.validators;


public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}

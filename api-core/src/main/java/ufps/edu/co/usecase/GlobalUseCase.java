package ufps.edu.co.usecase;

import java.util.List;

import ufps.edu.co.records.InputRequest;
import ufps.edu.co.records.OutputResponse;

public interface GlobalUseCase<C extends InputRequest, U extends InputRequest, O extends OutputResponse, ID> {
    O create(C input);
    O update(ID id, U input);
    O findById(ID id);
    List<O> findAll();
    void deleteById(ID id);
}

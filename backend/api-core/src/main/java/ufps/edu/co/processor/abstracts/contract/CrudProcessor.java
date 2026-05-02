package ufps.edu.co.processor.abstracts.contract;

import java.util.List;

import ufps.edu.co.records.OutputResponse;
import ufps.edu.co.records.contracts.*;

public interface CrudProcessor<C extends CreateType, U extends UpdateType, D extends DeleteType, P extends PatchType, F extends FindType, O extends OutputResponse> {
    O create(C input);

    O update(U input);

    void delete(D input);

    O patch(P input);

    O findById(F input);

    List<O> findAll();
}

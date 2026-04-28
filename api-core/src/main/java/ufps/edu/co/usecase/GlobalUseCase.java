package ufps.edu.co.usecase;

import java.util.List;

import ufps.edu.co.records.OutputResponse;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

public interface GlobalUseCase<C extends CreateType, U extends UpdateType, D extends DeleteType, P extends PatchType, F extends FindType, O extends OutputResponse> {
    O create(C input);

    O update(U input);

    O patch(P input);

    O findById(F id);

    List<O> findAll();

    void deleteById(D id);
}

package ufps.edu.co.maps;

import java.util.List;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import ufps.edu.co.records.InputRequest;
import ufps.edu.co.records.OutputResponse;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

@AllArgsConstructor
public abstract class GlobalMapper<
    C extends CreateType, 
    U extends UpdateType, 
    D extends DeleteType,
    P extends PatchType,
    F extends FindType,
    O extends OutputResponse, 
    DTO> {

    private final Class<C> createClass;
    private final Class<U> updateClass;
    private final Class<D> deleteClass;
    private final Class<P> patchClass;
    private final Class<F> findClass;

    /**
     * Método final que realiza el dispatch centralizado.
     * Llama a los métodos específicos según el tipo de InputRequest.
     */
    public final DTO toDto(InputRequest input) {
        if (createClass != null && createClass.isInstance(input)) {
            return toDtoCreate(createClass.cast(input));
        }
        if (updateClass != null && updateClass.isInstance(input)) {
            return toDtoUpdate(updateClass.cast(input));
        }
        if (deleteClass != null && deleteClass.isInstance(input)) {
            return toDtoDelete(deleteClass.cast(input));
        }
        if (patchClass != null && patchClass.isInstance(input)) {
            return toDtoPatch(patchClass.cast(input));
        }
        if (findClass != null && findClass.isInstance(input)) {
            return toDtoFind(findClass.cast(input));
        }
        throw new IllegalArgumentException(
                "Unsupported input type for " + getClass().getSimpleName() + ": " + input.getClass().getName());
    }

    /**
     * Métodos abstractos obligatorios para cada mapper concreto.
     */
    protected abstract DTO toDtoCreate(C input);

    protected abstract DTO toDtoUpdate(U input);

    protected abstract DTO toDtoDelete(D input);

    protected abstract DTO toDtoPatch(P input);

    protected abstract DTO toDtoFind(F input);

    public abstract O toOutput(DTO dto);

    protected <T, R> R mapOrNull(T value, Function<T, R> mapper) {
        return value != null ? mapper.apply(value) : null;
    }

    protected <T, R> List<R> mapListOrNull(List<T> list, Function<T, R> mapper) {
        if (list == null || list.isEmpty()) return null;
        return list.stream().map(mapper).toList();
    }
}

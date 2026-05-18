package ufps.edu.co.maps;

import lombok.AllArgsConstructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import ufps.edu.co.records.InputRequest;
import ufps.edu.co.records.OutputResponse;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

@AllArgsConstructor
public abstract class GlobalMapper<C extends CreateType, U extends UpdateType, D extends DeleteType, P extends PatchType, F extends FindType, O extends OutputResponse, DTO> {

    private Class<C> createClass;
    private Class<U> updateClass;
    private Class<D> deleteClass;
    private Class<P> patchClass;
    private Class<F> findClass;

    /**
     * WARNING
     * DESHACERNOS DE ESTE BLOQUE SI LLEGA A HABER ERRORES CON LOS MAPEADORES
     */
    /**
     * No-arg constructor: intenta inferir las clases genéricas de la subclase
     * para evitar tener que pasar manualmente los Class en cada subclase.
     */
    protected GlobalMapper() {
        Type superType = getClass().getGenericSuperclass();
        if (superType instanceof ParameterizedType) {
            Type[] args = ((ParameterizedType) superType).getActualTypeArguments();
            this.createClass = asClass(args, 0);
            this.updateClass = asClass(args, 1);
            this.deleteClass = asClass(args, 2);
            this.patchClass = asClass(args, 3);
            this.findClass = asClass(args, 4);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> asClass(Type[] args, int index) {
        if (args == null || args.length <= index)
            return null;
        Type t = args[index];
        if (t instanceof Class)
            return (Class<T>) t;
        if (t instanceof ParameterizedType)
            return (Class<T>) ((ParameterizedType) t).getRawType();
        return null;
    }

    /**
     * FIN DE BLOQUE DE INFERENCIA AUTOMÁTICA DE CLASES GENÉRICAS, NO ELIMINAR LO
     * QUE HAY MÁS ABAJO SI HAY ERRORES EN LOS MAPEADORES, SOLO ESTE BLOQUE DE
     * ARRIBA SI ES NECESARIO
     */

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
}

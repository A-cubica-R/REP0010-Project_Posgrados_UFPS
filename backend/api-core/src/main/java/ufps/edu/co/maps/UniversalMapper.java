package ufps.edu.co.maps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ufps.edu.co.domain.annotations.UniversalMapping;
import ufps.edu.co.records.InputRequest;
import ufps.edu.co.records.OutputResponse;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

public abstract class UniversalMapper<O extends OutputResponse, DTO> {

    private final Class<? extends CreateType> createClass;
    private final Class<? extends UpdateType> updateClass;
    private final Class<? extends DeleteType> deleteClass;
    private final Class<? extends PatchType> patchClass;
    private final Class<? extends FindType> findClass;

    protected UniversalMapper() {
        UniversalMapping mapping = getClass().getAnnotation(UniversalMapping.class);
        if (mapping == null) {
            throw new IllegalStateException(
                    "Missing @UniversalMapping on " + getClass().getSimpleName());
        }
        this.createClass = mapping.create();
        this.updateClass = mapping.update();
        this.deleteClass = mapping.delete();
        this.patchClass = mapping.patch();
        this.findClass = mapping.find();
    }

    public final DTO toDto(InputRequest input) {
        if (input == null) {
            return null;
        }
        if (createClass.isInstance(input)) {
            return invokeDtoMethod("toDtoCreate", input);
        }
        if (updateClass.isInstance(input)) {
            return invokeDtoMethod("toDtoUpdate", input);
        }
        if (deleteClass.isInstance(input)) {
            return invokeDtoMethod("toDtoDelete", input);
        }
        if (patchClass.isInstance(input)) {
            return invokeDtoMethod("toDtoPatch", input);
        }
        if (findClass.isInstance(input)) {
            return invokeDtoMethod("toDtoFind", input);
        }
        throw new IllegalArgumentException(
                "Unsupported input type for " + getClass().getSimpleName() + ": " + input.getClass().getName());
    }

    @SuppressWarnings("unchecked")
    private DTO invokeDtoMethod(String methodName, Object input) {
        try {
            Method method = findSingleArgumentMethod(methodName, input.getClass());
            Object result = method.invoke(this, input);
            return (DTO) result;
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(
                    "Missing mapper method " + methodName + "(" + input.getClass().getSimpleName() + ") in "
                            + getClass().getSimpleName(),
                    e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(
                    "Error invoking " + methodName + " in " + getClass().getSimpleName(), e);
        }
    }

    private Method findSingleArgumentMethod(String methodName, Class<?> inputClass) throws NoSuchMethodException {
        Method exactMatch = null;
        for (Method method : getClass().getDeclaredMethods()) {
            if (!method.getName().equals(methodName) || method.getParameterCount() != 1) {
                continue;
            }
            Class<?> parameterType = method.getParameterTypes()[0];
            if (parameterType.equals(inputClass)) {
                exactMatch = method;
                break;
            }
            if (parameterType.isAssignableFrom(inputClass)) {
                exactMatch = method;
            }
        }
        if (exactMatch == null) {
            throw new NoSuchMethodException(methodName + "(" + inputClass.getName() + ")");
        }
        exactMatch.setAccessible(true);
        return exactMatch;
    }

    public abstract O toOutput(DTO dto);
}

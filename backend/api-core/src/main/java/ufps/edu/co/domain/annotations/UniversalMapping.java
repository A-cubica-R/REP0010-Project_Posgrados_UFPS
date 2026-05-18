package ufps.edu.co.domain.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UniversalMapping {

    Class<? extends CreateType> create();

    Class<? extends UpdateType> update();

    Class<? extends DeleteType> delete();

    Class<? extends PatchType> patch();

    Class<? extends FindType> find();
}
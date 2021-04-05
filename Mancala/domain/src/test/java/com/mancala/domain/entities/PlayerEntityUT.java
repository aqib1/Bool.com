package com.mancala.domain.entities;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.Test;

public class PlayerEntityUT {

    @Test
    public void collectionRequestTest() {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(PlayerEntity.class);
        Validator validator = ValidatorBuilder.create()
                .with(new SetterMustExistRule())
                .with(new GetterMustExistRule())
                .with(new GetterTester())
                .with(new SetterTester())
                .build();
        validator.validate(pojoClass);
    }
}

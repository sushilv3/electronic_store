package com.sushil.electronic.store.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {
    private Logger log = LoggerFactory.getLogger(ImageNameValidator.class);
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      log.info("logger called from Custom(ImageName) validator: "+value);
        if (value.isBlank()) {
            return false;
        } else {
            return true;
        }
//        return false;
    }
}

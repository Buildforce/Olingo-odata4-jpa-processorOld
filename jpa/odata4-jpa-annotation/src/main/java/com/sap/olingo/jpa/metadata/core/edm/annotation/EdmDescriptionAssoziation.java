package com.sap.olingo.jpa.metadata.core.edm.annotation;

import java.lang.annotation.*;

/**
 * Marks an association to an entity that contains a language/locale dependent description of a coded value. E.g the
 * Association to a country name for an attribute containing the country iso code.<p>
 * The association shall be annotated as OneToOne or OneToMany (as the key is not completely given), insertable = false
 * and updatable = false. It is mapped to a property with the name of the association<p>
 * One and only one of the fields of LanguageAttribute - LocaleAttribute has to be filled. To ensure that, in case
 * multiple descriptions are available, the right one the chosen the Description Attribute has to be named<p>
 * @author Oliver Grande
 *
 * @deprecated
 * This annotation is no longer acceptable due its misspelling.
 *  <p> Use {@link EdmDescriptionAssociation} instead.
 *
 */

@Deprecated
@Target({ ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
public @interface EdmDescriptionAssoziation {
  String descriptionAttribute();

  String languageAttribute() default "";

  String localeAttribute() default "";

  @interface valueAssignment {
    String attribute() default "";

    String value() default "";
  }

  valueAssignment[] valueAssignments() default {};

}
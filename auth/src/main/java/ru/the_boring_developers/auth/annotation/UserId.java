
package ru.the_boring_developers.auth.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для резолва идентификатора пользователя<br>
 * в качестве аргумента метода
 */
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface UserId {
}

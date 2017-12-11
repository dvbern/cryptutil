package ch.dvbern.lib.cryptutil.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Marker annotation: passed instances <strong>must be != null</strong>
 *
 * We roll our own because we do not want additional dependencies!
 * Detailed semantics, see <a href="https://checkerframework.org/manual/#nullness-checker>Checker Framework</a>
 */
@Target({ ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.TYPE,
		ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
@Retention(SOURCE)
public @interface NonNull {
}

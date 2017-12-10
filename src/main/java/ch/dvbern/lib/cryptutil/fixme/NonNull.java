package ch.dvbern.lib.cryptutil.fixme;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value = { ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.TYPE,
		ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
public @interface NonNull {
}

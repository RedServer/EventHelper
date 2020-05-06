package com.gamerforea.eventhelper.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import com.google.common.io.ByteStreams;

@SuppressWarnings("UnstableApiUsage")
public final class InjectionUtils {

	private static final Method defineClass;

	/**
	 * Need Inj subclass
	 */
	public static Class<?> injectClass(ClassLoader loader, Class<?> parent) throws ReflectiveOperationException {
		try (InputStream in = parent.getClassLoader().getResourceAsStream(parent.getName().replace('.', '/') + "$Inj.class")) {
			byte[] bytes = ByteStreams.toByteArray(in);
			return (Class<?>)defineClass.invoke(loader, null, bytes, 0, bytes.length);
		} catch (IOException e) {
			throw new RuntimeException("Unable to define class", e);
		}
	}

	static {
		try {
			defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
			defineClass.setAccessible(true);
		} catch (Throwable e) {
			throw new RuntimeException("Failed hooking ClassLoader.defineClass(String, byte[], int, int) method!", e);
		}
	}
}

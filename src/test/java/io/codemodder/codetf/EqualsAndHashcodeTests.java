package io.codemodder.codetf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public interface EqualsAndHashcodeTests<T> {

  /**
   * @return a new instance of the class under test
   */
  T createInstance();

  /**
   * @return a new instance of the class under test that is equal to the instance returned by {@link
   *     #createInstance()}
   */
  default T createEqualInstance() {
    return createInstance();
  }

  /**
   * @return a new instance of the class under test that is different from the instance returned by
   *     {@link #createInstance()}
   */
  T createDifferentInstance();

  @Test
  default void testEquals() {
    final T instance = createInstance();
    final T equalInstance = createEqualInstance();
    final T differentInstance = createDifferentInstance();

    assertEquals(instance, equalInstance, "Instances should be equal");
    assertNotEquals(instance, differentInstance, "Instances should not be equal");
    assertNotEquals(equalInstance, differentInstance, "Instances should not be equal");
  }

  @Test
  default void testHashCode() {
    final T instance = createInstance();
    final T equalInstance = createEqualInstance();
    final T differentInstance = createDifferentInstance();

    assertEquals(instance.hashCode(), equalInstance.hashCode(), "Hash codes should be equal");
    assertNotEquals(
        instance.hashCode(), differentInstance.hashCode(), "Hash codes should not be equal");
  }
}

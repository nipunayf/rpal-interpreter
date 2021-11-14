package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.tree.node.Node;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Template class for the standardizing builder.
 * Implements the singleton design pattern to minimize the memory usage.
 * This class must be extended and implement the standardize method to standardize the respective node.
 */
public abstract class AbstractBuilder {

    private static final Map<Class<? extends AbstractBuilder>, AbstractBuilder> INSTANCES_MAP = new HashMap<>();

    public static AbstractBuilder getInstance(Class<? extends AbstractBuilder> classInstance) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (INSTANCES_MAP.containsKey(classInstance)) {
            return INSTANCES_MAP.get(classInstance);
        } else {
            AbstractBuilder instance = classInstance.getConstructor().newInstance();
            INSTANCES_MAP.put(classInstance, instance);
            return instance;
        }
    }

    /**
     * Returns the standardized subtree of which the given node is the root.
     *
     * @param node to be standardized
     */
    public abstract void standardize(Node node) throws NoSuchMethodException, CloneNotSupportedException;
}
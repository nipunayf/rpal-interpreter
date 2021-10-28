package nipunayf.rpalinterpreter.tree.factory;

import nipunayf.rpalinterpreter.tree.node.Node;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFactory {

    private static final Map<Class<? extends AbstractFactory>, AbstractFactory> INSTANCES_MAP = new HashMap<>();

    public static AbstractFactory getInstance(Class<? extends AbstractFactory> classInstance) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (INSTANCES_MAP.containsKey(classInstance)) {
            return INSTANCES_MAP.get(classInstance);
        } else {
            AbstractFactory instance = classInstance.getConstructor().newInstance();
            INSTANCES_MAP.put(classInstance, instance);
            return instance;
        }
    }

    /**
     * Returns the standardized subtree of which the given node is the root.
     *
     * @param node to be standardized
     */
    public abstract void standardize(Node node) throws NoSuchMethodException;
}
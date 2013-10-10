package vo.gateway;

import vo.Node;

/**
 * User: dorid
 * Date: 13-10-7
 * Time: 16:10
 */
public class ExclusiveGateway extends Node {
    @Override
    public String getShape() {
        return "diamond";
    }

    @Override
    public String getXlabel() {
        return getName();
    }

    @Override
    public String getLabel() {
        return "X";
    }
}

package org.assemblits.eru.gui.dynamo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.assemblits.eru.gui.dynamo.Dynamo;
import org.assemblits.eru.scene.control.Display;

/**
 * Created by mtrujillo on 8/24/17.
 */
public class EruDisplay extends Display implements ValuableDynamo {

    /**
     * The map to linkToConnections {@code EruDisplay} and {@code Tags}. This map is useful for
     * finding a specific EruDisplay within the scene graph and get the setted tag. While the id of a Node
     * should be unique within the scene graph, this uniqueness is supported by the {@code ComponentsIdsGenerator}.
     */
    private IntegerProperty currentValueTagID;

    public EruDisplay() {
        super();
        this.currentValueTagID = new SimpleIntegerProperty(this, "currentValueTagID", -1);
    }

    @Override
    public void setCurrentTagValue(String value) {
        setCurrentText(value);
    }

    @Override
    public String getCurrentTagValue() {
        return getCurrentText();
    }

    @Override
    public Integer getCurrentValueTagID() {
        return currentValueTagID.get();
    }

    @Override
    public IntegerProperty currentValueTagIDProperty() {
        return currentValueTagID;
    }

    @Override
    public void setCurrentValueTagID(Integer currentValueTagID) {
        this.currentValueTagID.set(currentValueTagID);
    }
}

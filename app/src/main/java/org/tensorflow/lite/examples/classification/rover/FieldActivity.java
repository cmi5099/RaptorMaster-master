package org.tensorflow.lite.examples.classification.rover;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class FieldActivity {
    double fieldWidth;
    double fieldLength;

    double blockWidth = 2.5;
    double blockLength = 5;

    int blocksPerLane;
    int lanes;

    static int notSearched = 0;
    static int searched = 1;
    static int found = 2;

    //Sets blocksPerLane, lanes
    void calculateFieldParams(double fieldWidth, double fieldLength) {
        lanes = (int) (fieldWidth / blockWidth);
        blocksPerLane = (int) (fieldLength / blockLength);
    }

    //Does math to calculate the degree at which the rover should turn its wheels in order to get
    //back on track
    void calculateTurningRadius() {

    }

    //Creates the grid similar to battleship (Map<string> grid = new Map())
    //Use above values to create A(1) - A(blocksPerLane), B(1) - B(blocksPerLane), etc.
    void createGrid() {

    }

    //Changes value next to key from notSearched to searched or found
    void updateGrid() {

    }

    Map<Integer, Integer> grid = new Map<Integer, Integer>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsValue(@Nullable Object value) {
            return false;
        }

        @Nullable
        @Override
        public Integer get(@Nullable Object key) {
            return null;
        }

        @Nullable
        @Override
        public Integer put(Integer key, Integer value) {
            return null;
        }

        @Nullable
        @Override
        public Integer remove(@Nullable Object key) {
            return null;
        }

        @Override
        public void putAll(@NonNull Map<? extends Integer, ? extends Integer> m) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public Set<Integer> keySet() {
            return null;
        }

        @NonNull
        @Override
        public Collection<Integer> values() {
            return null;
        }

        @NonNull
        @Override
        public Set<Entry<Integer, Integer>> entrySet() {
            return null;
        }

    };
}

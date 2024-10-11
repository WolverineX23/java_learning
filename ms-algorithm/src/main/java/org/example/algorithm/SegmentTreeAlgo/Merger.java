package org.example.algorithm.SegmentTreeAlgo;

/**
 * 融合器接口
 */
public interface Merger<E> {
    E merge(E a, E b);
}

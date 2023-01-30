package com.haiprj.base.utils;

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class GameRandom extends Random {

    public GameRandom() {
    }

    public GameRandom(long seed) {
        super(seed);
    }

    @Override
    public synchronized void setSeed(long seed) {
        super.setSeed(seed);
    }

    @Override
    protected int next(int bits) {
        return super.next(bits);
    }

    @Override
    public void nextBytes(byte[] bytes) {
        super.nextBytes(bytes);
    }

    @Override
    public int nextInt() {
        return super.nextInt();
    }

    @Override
    public int nextInt(int bound) {
        return super.nextInt(bound);
    }

    @Override
    public long nextLong() {
        return super.nextLong();
    }

    @Override
    public boolean nextBoolean() {
        return super.nextBoolean();
    }

    @Override
    public float nextFloat() {
        return super.nextFloat();
    }

    @Override
    public double nextDouble() {
        return super.nextDouble();
    }

    @Override
    public synchronized double nextGaussian() {
        return super.nextGaussian();
    }

    @Override
    public IntStream ints(long streamSize) {
        return super.ints(streamSize);
    }

    @Override
    public IntStream ints() {
        return super.ints();
    }

    @Override
    public IntStream ints(long streamSize, int randomNumberOrigin, int randomNumberBound) {
        return super.ints(streamSize, randomNumberOrigin, randomNumberBound);
    }

    @Override
    public IntStream ints(int randomNumberOrigin, int randomNumberBound) {
        return super.ints(randomNumberOrigin, randomNumberBound);
    }

    @Override
    public LongStream longs(long streamSize) {
        return super.longs(streamSize);
    }

    @Override
    public LongStream longs() {
        return super.longs();
    }

    @Override
    public LongStream longs(long streamSize, long randomNumberOrigin, long randomNumberBound) {
        return super.longs(streamSize, randomNumberOrigin, randomNumberBound);
    }

    @Override
    public LongStream longs(long randomNumberOrigin, long randomNumberBound) {
        return super.longs(randomNumberOrigin, randomNumberBound);
    }

    @Override
    public DoubleStream doubles(long streamSize) {
        return super.doubles(streamSize);
    }

    @Override
    public DoubleStream doubles() {
        return super.doubles();
    }

    @Override
    public DoubleStream doubles(long streamSize, double randomNumberOrigin, double randomNumberBound) {
        return super.doubles(streamSize, randomNumberOrigin, randomNumberBound);
    }

    @Override
    public DoubleStream doubles(double randomNumberOrigin, double randomNumberBound) {
        return super.doubles(randomNumberOrigin, randomNumberBound);
    }
}

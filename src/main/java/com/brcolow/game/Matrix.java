package com.brcolow.game;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorShuffle;

/**
 * From: https://github.com/JOML-CI/joml-bench/blob/d5e1de16a2984a067525e8666dfd2a882e8a877c/src/bench/Matrix4fvArr.java
 */
public class Matrix {
    private static final VectorShuffle<Float> s0000 = FloatVector.SPECIES_128.shuffleFromValues(0, 0, 0, 0);
    private static final VectorShuffle<Float> s1111 = FloatVector.SPECIES_128.shuffleFromValues(1, 1, 1, 1);
    private static final VectorShuffle<Float> s2222 = FloatVector.SPECIES_128.shuffleFromValues(2, 2, 2, 2);
    private static final VectorShuffle<Float> s3333 = FloatVector.SPECIES_128.shuffleFromValues(3, 3, 3, 3);
    static final VectorShuffle<Float> s00004444 = FloatVector.SPECIES_256.shuffleFromValues(0, 0, 0, 0, 4, 4, 4, 4); // _MM_SHUFFLE(0, 0, 0, 0)
    static final VectorShuffle<Float> s11115555 = FloatVector.SPECIES_256.shuffleFromValues(1, 1, 1, 1, 5, 5, 5, 5); // _MM_SHUFFLE(1, 1, 1, 1)
    static final VectorShuffle<Float> s22226666 = FloatVector.SPECIES_256.shuffleFromValues(2, 2, 2, 2, 6, 6, 6, 6); // _MM_SHUFFLE(2, 2, 2, 2)
    static final VectorShuffle<Float> s33337777 = FloatVector.SPECIES_256.shuffleFromValues(3, 3, 3, 3, 7, 7, 7, 7); // _MM_SHUFFLE(3, 3, 3, 3)
    static final VectorShuffle<Float> s01230123 = FloatVector.SPECIES_256.shuffleFromValues(0, 1, 2, 3, 0, 1, 2, 3); // _mm256_permute2f128_ps(..., 0x00);
    static final VectorShuffle<Float> s45674567 = FloatVector.SPECIES_256.shuffleFromValues(4, 5, 6, 7, 4, 5, 6, 7); // _mm256_permute2f128_ps(..., 0x11);

    public static float[] mul_4x4_128(final float[] a, final float[] b) {
        float[] result = new float[16];
        FloatVector row1 = FloatVector.fromArray(FloatVector.SPECIES_128, b, 0);
        FloatVector row2 = FloatVector.fromArray(FloatVector.SPECIES_128, b, 4);
        FloatVector row3 = FloatVector.fromArray(FloatVector.SPECIES_128, b, 8);
        FloatVector row4 = FloatVector.fromArray(FloatVector.SPECIES_128, b, 12);
        for (int i = 0; i < 4; i++) {
            FloatVector r = FloatVector.fromArray(FloatVector.SPECIES_128, a, i << 2);
            // _mm_set1_ps(A[4*i + 0])
            FloatVector b0 = r.rearrange(s0000);
            // _mm_set1_ps(A[4*i + 1])
            FloatVector b1 = r.rearrange(s1111);
            // _mm_set1_ps(A[4*i + 2])
            FloatVector b2 = r.rearrange(s2222);
            // _mm_set1_ps(A[4*i + 3])
            FloatVector b3 = r.rearrange(s3333);
            b0.fma(row1, b1.fma(row2, b2.fma(row3, b3.mul(row4)))).intoArray(result, i << 2);
        }
        return result;
    }

    public static float[] mul_4x4_256(final float[] a, final float[] b) {
        float[] result = new float[16];
        FloatVector t0 = FloatVector.fromArray(FloatVector.SPECIES_256, a, 0);
        FloatVector t1 = FloatVector.fromArray(FloatVector.SPECIES_256, a, 8);
        FloatVector u0 = FloatVector.fromArray(FloatVector.SPECIES_256, b, 0);
        FloatVector u1 = FloatVector.fromArray(FloatVector.SPECIES_256, b, 8);
        FloatVector u0r00 = u0.rearrange(s01230123);
        FloatVector u1r00 = u1.rearrange(s01230123);
        FloatVector u0r11 = u0.rearrange(s45674567);
        FloatVector u1r11 = u1.rearrange(s45674567);
        t0.rearrange(s00004444).fma(u0r00, t0.rearrange(s11115555).mul(u0r11))
                .add(t0.rearrange(s33337777).fma(u1r11, t0.rearrange(s22226666).mul(u1r00)))
                .intoArray(result, 0);
        t1.rearrange(s00004444).fma(u0r00, t1.rearrange(s11115555).mul(u0r11))
                .add(t1.rearrange(s33337777).fma(u1r11, t1.rearrange(s22226666).mul(u1r00)))
                .intoArray(result, 8);
        return result;
    }

    public static float[] mul4x4(final float[] a, final float[] b) {
        float[] result = new float[16];
        int j, k;
        for (int i = 0; i < 16; i++) {
            j = (i / 4) * 4;
            k = (i % 4);
            result[i] = (a[j] * b[k]) + (a[j + 1] * b[k + 4]) + (a[j + 2] * b[k + 8]) + (a[j + 3] * b[k + 12]);
        }
        return result;
    }

    public static float[] normalizeVec3FastInvSqrt(final float[] a) {
        float[] result = new float[3];
        float scalar = invSqrt(Math.fma(a[0], a[0], Math.fma(a[1], a[1], a[2] * a[2])));
        result[0] = a[0] * scalar;
        result[1] = a[1] * scalar;
        result[2] = a[2] * scalar;
        return result;
    }

    public static float[] normalizeVec3SlowInvSqrt(final float[] a) {
        float[] result = new float[3];
        float scalar = 1.0f / (float) Math.sqrt(Math.fma(a[0], a[0], Math.fma(a[1], a[1], a[2] * a[2])));
        result[0] = a[0] * scalar;
        result[1] = a[1] * scalar;
        result[2] = a[2] * scalar;
        return result;
    }

    public static float invSqrt(float x) {
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);
        x *= 1.5f - xhalf * x * x;
        return x;
    }

}

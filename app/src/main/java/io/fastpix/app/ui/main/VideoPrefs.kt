package io.fastpix.app.ui.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


val dummyData = listOf<DummyData>(
    DummyData(
        "3e8a1bd0-b0f9-4539-90e7-de53fd46696f",
        "https://stream.fastpix.co/3e8a1bd0-b0f9-4539-90e7-de53fd46696f.m3u8",
    ),
    DummyData(
        "86520338-ede2-409e-b44e-da1dd4f73783",
        "https://stream.fastpix.io/86520338-ede2-409e-b44e-da1dd4f73783.m3u8",
    ),
    DummyData(
        "7e7167ce-13dc-47e7-bad1-1541907ba3d4",
        "https://stream.fastpix.io/7e7167ce-13dc-47e7-bad1-1541907ba3d4.m3u8",
    ),
    DummyData(
        "5272e13b-4454-4622-8268-4b3f17970f2c",
        "https://stream.fastpix.co/5272e13b-4454-4622-8268-4b3f17970f2c.m3u8",
    ),
    DummyData(
        "16ac212a-0f4f-49c5-9fd7-a42d9ff61541",
        "https://stream.fastpix.io/16ac212a-0f4f-49c5-9fd7-a42d9ff61541.m3u8",
    ),
    DummyData(
        "3ad91e5f-0f45-403f-bda0-2a668a3581ee",
        "https://stream.fastpix.io/3ad91e5f-0f45-403f-bda0-2a668a3581ee.m3u8",
    ),
    DummyData(
        "46c09d0c-d97a-44b2-9737-c5e6daf30a41",
        "https://stream.fastpix.io/46c09d0c-d97a-44b2-9737-c5e6daf30a41.m3u8",
    ),
    DummyData(
        "d81105da-c78a-482e-b1dc-d89e10d8d682",
        "https://stream.fastpix.io/d81105da-c78a-482e-b1dc-d89e10d8d682.m3u8",
    ),
    DummyData(
        "f19268f5-9719-403f-87c9-b604fb3bdce3",
        "https://stream.fastpix.io/f19268f5-9719-403f-87c9-b604fb3bdce3.m3u8",
    ),
    DummyData(
        "112a2222-0f31-44a0-bcf6-30cfa6e1d17d",
        "https://stream.fastpix.io/112a2222-0f31-44a0-bcf6-30cfa6e1d17d.m3u8",
    ),
    DummyData(
        "ca854fd4-a3d0-4525-bd43-80de50887e1a",
        "https://stream.fastpix.io/ca854fd4-a3d0-4525-bd43-80de50887e1a.m3u8",
    ),
)


@Parcelize
data class DummyData(
    var id: String,
    var url: String,
) : Parcelable
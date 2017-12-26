package com.yevhen_stepiuk.testgallery.extension


fun List<String>.concatToSingleString() = reduceIndexed { idx, acc, s -> acc + if (lastIndex == idx) s else "$s, " }
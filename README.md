# KUnits
Unit Conversion engine for the JVM.

# Usage

Create an instance of the conversion engine:
````kotlin
val engine = UnitConversionEngine()
````

use it to convert between units without exception handling:
```kotlin
engine.convert(amount = 25.0, from = "N/cm2", to = "lbf/in2")
```

Or make it safer:
```kotlin
// Fails: there is no way to convert from Newtons to Inches
try {
    engine.convert(amount = 2.0, from = "N", to = "in")
} catch (e: ConversionException) {
    // ...
}
```

Or alternatively, you can first check if a given conversion is possible:
```kotlin
if (engine.canConvert(from = "N", to = "cm")) {
    engine.convert(amount = 25.0, from = "N", to = "cm")
} else {
    // ...
}
```
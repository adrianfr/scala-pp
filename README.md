scalapp
=======

Scala pretty-print.

Prints nicely any arbitrarily-nested scala collection.

git clone https://github.com/adrianfr/scalapp.git

cd scalapp

mvn package

```
scala -cp target/scalapp-1.0-SNAPSHOT.jar

scala> import af.scalapp._
import af.scalapp._

scala> 42.pp("the answer")
the answer: 42
res0: Int = 42

scala> List(1,2,3,("a", 2.5)).pp
[
  1
  2
  3
  (a,2.5)
]
res1: List[Any] = List(1, 2, 3, (a,2.5))

scala> List(1,2,(3,4),("a", 2.5), Map(1->Array(22,"x"), 2->Set("boo", "foo"))).pp
[
  1
  2
  (3,4)
  (a,2.5)
  <
    (
      1
      [22,x]
    )
    (
      2
      {boo,foo}
    )
  >
]
res2: List[Any] = List(1, 2, (3,4), (a,2.5), Map(1 -> Array(22, x), 2 -> Set(boo, foo)))
```


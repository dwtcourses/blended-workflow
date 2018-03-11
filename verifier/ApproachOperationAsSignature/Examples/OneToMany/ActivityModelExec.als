module filesystem/Examples/OneToMany/ActivityModelExec

open filesystem/ActivityModelExec
open filesystem/Examples/OneToMany/ActivityModel

run final for 2 but 4 State, 4 Activity, 5 Int

assert PreservesInvariant {
	all s, s': State, a: Activity |
		Invariants[s] and exec[s, s', a] => Invariants[s']
}
check PreservesInvariant for 2 but 4 State, 3 Activity, 5 Int

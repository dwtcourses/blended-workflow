module filesystem/doctorappointment/patientepisode/SecureDoctorAppointment

open filesystem/doctorappointment/patientepisode/DoctorAppointment
open filesystem/BWSecureSpec

one sig Alice, Bob extends User{}

one sig R_Doctor, R_Bob extends RoleSubject{}

////////////////////////////PATTERN2////////////////////////

sig Doctor extends Obj{}
one sig doctor_episode extends FName {} 
one sig episode_doctor extends FName {} 
one sig episode_report extends FName {}

one sig DoctorAlice extends Doctor{}

fact user_objs{
	Alice.usr_obj = DoctorAlice
	no Bob.usr_obj
}

one sig DomainEpisodeDoctor extends DomainSubject {}

fact domain_doctor{
	DomainEpisodeDoctor.path = {0 -> episode_doctor}
}

fact relations {
	// relation Patient - Episode
	doctor_episode.minMul = 0
	doctor_episode.maxMul = 10
	doctor_episode.inverse = episode_doctor

	episode_doctor.minMul = 1
	episode_doctor.maxMul = 10
	episode_doctor.inverse = doctor_episode
}


pred pattern2Complete {
 	one s: AbstractState | 
		// cannot be the initial state to find one meaningful state
		#Patient <: s.objects = 1 and
		#Episode <: s.objects = 1 and
		#Doctor <: s.objects = 1 and
		#s.objects = 3 and
		// model is well defined

		// all attributes are defined
		attributesDefined [s, Patient, patient_name + patient_address]	and
		attributesDefined [s, Episode, episode_reserve_date + episode_report] and

		// associations multiplicity
		multiplicityRule [s, Episode, episode_patient] and
		multiplicityRule [s, Patient, patient_episode] and

		
		multiplicityRule [s, Episode, episode_doctor] and
		multiplicityRule [s, Doctor, doctor_episode] and


		// bidirectional relation
		bidirectionalRule [s, Patient, episode_patient, Episode, patient_episode] and
		bidirectionalRule [s, Doctor, episode_doctor , Episode, doctor_episode] and

		// dependencies hold
		checkDependence [s, Episode, reserve_date_dependence]
}


pred pattern2Invariants(s: AbstractState) {
	// no extra fields
	noExtraFields [s, Patient, patient_name + patient_address + patient_episode] 	 	
	noExtraFields [s, Episode, episode_reserve_date + episode_patient + episode_report + episode_doctor]	
	noExtraFields [s, Doctor, doctor_episode]

	// does not exceeds mutliplicity
	noMultiplicityExceed [s, Episode, episode_patient] 
	noMultiplicityExceed [s, Patient, patient_episode] 
	
	noMultiplicityExceed [s, Episode, episode_doctor] 
	noMultiplicityExceed [s, Doctor, doctor_episode] 

	// if there is a link between two objects, either is unidirectional or bidirectional
	bidirectionalPreservation [s, Patient, episode_patient, Episode, patient_episode] 

	bidirectionalPreservation [s, Doctor, episode_doctor, Episode, doctor_episode] 

	// dependencies hold
	checkDependence [s, Episode, reserve_date_dependence]
}

////////////////////////////////////////////////////////////////////////////////////////////////////

run{}

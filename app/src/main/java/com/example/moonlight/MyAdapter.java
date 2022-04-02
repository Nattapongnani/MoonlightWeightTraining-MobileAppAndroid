package com.example.moonlight;

public class MyAdapter {

    int idExercise;
    String exerciseName;
    int sets;
    double weight;
    int reps;
    double totalVolume;
    int inProgramId;

    int idProgram;
    String programName;
    String createDate;
    int workouts;
    double trainingVolume;
    String lastPerformed;
    int sessionCount;

    int idPerformed;
    String performedDate;
    int fromProgramId;
    double volumes;

    public MyAdapter(){
    }

    // for exercise selected
    public MyAdapter(String _exerciseName, int _sets, double _weight, int _reps, double _totalVolume){
        this.exerciseName = _exerciseName;
        this.sets =_sets;
        this.weight = _weight;
        this.reps = _reps;
        this.totalVolume = _totalVolume;
    }

    public MyAdapter(int _id, String _exerciseName, int _sets, double _weight, int _reps, double _totalVolume, int _inProgramId){
        this.idExercise = _id;
        this.exerciseName = _exerciseName;
        this.sets =_sets;
        this.weight = _weight;
        this.reps = _reps;
        this.totalVolume = _totalVolume;
        this.inProgramId = _inProgramId;
    }

    //for program
    public MyAdapter(String _programName, String _createDate, int _workouts, double _trainingVolume, String _lastPerformed, int _sessionCount){
        this.programName = _programName;
        this.createDate = _createDate;
        this.workouts = _workouts;
        this.trainingVolume = _trainingVolume;
        this.lastPerformed =_lastPerformed;
        this.sessionCount =_sessionCount;
    }

    public MyAdapter(int _id, String _programName, String _createDate, int _workouts, double _trainingVolume, String _lastPerformed, int _sessionCount){
        this.idProgram = _id;
        this.programName = _programName;
        this.createDate = _createDate;
        this.workouts = _workouts;
        this.trainingVolume = _trainingVolume;
        this.lastPerformed =_lastPerformed;
        this.sessionCount =_sessionCount;
    }

    public MyAdapter(int _id, String _programName, double _trainingVolume) {
        this.idProgram = _id;
        this.programName = _programName;
        this.trainingVolume = _trainingVolume;
    }
    // for performed
    public MyAdapter(String _performedDate, int _fromProgramId, double _volumes) {
        this.performedDate = _performedDate;
        this.fromProgramId = _fromProgramId;
        this.volumes = _volumes;
    }

    public MyAdapter(int _id, String _performedDate, int _fromProgramId, double _volumes) {
        this.idPerformed = _id;
        this.performedDate = _performedDate;
        this.fromProgramId = _fromProgramId;
        this.volumes = _volumes;
    }

    // set and get for exercise
    public void setIdExercise(int _id) {
        this.idExercise = _id;
    }

    public int getIdExercise() {
        return this.idExercise;
    }

    public void setExerciseName(String _exerciseName) {
        this.exerciseName = _exerciseName;
    }

    public String getExerciseName(){
        return this.exerciseName;
    }

    public void setSets(int _sets) {
        this.sets = _sets;
    }

    public int getSets(){
        return this.sets;
    }

    public void setWeight(double _weight) {
        this.weight = _weight;
    }

    public double getWeight(){
        return this.weight;
    }

    public void setReps(int _reps) {
        this.reps = _reps;
    }

    public int getReps(){
        return this.reps;
    }

    public void setTotalVolume(double _totalVolume) {
        this.totalVolume = _totalVolume;
    }

    public double getTotalVolumes(){
        return this.totalVolume;
    }

    public void setInProgramId(int _inProgramId) {
        this.inProgramId = _inProgramId;
    }

    public int getInProgramId(){
        return this.inProgramId;
    }

    // set and get for program
    public void setIdProgram(int _id) {
        this.idProgram = _id;
    }

    public int getIdProgram() {
        return this.idProgram;
    }

    public void setProgramName(String _programName) {
        this.programName = _programName;
    }

    public String getProgramName() {
        return this.programName;
    }

    public void setCreateDate(String _createDate) {
        this.createDate = _createDate;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setWorkouts(int _workouts) {
        this.workouts = _workouts;
    }

    public int getWorkouts() {
        return this.workouts;
    }

    public void setTrainingVolume(double _trainingVolume) {
        this.trainingVolume = _trainingVolume;
    }

    public double getTrainingVolume() {
        return this.trainingVolume;
    }

    public void setLastPerformed(String _programName) {
        this.lastPerformed = _programName;
    }

    public String getLastPerformed() {
        return this.lastPerformed;
    }

    public void setSessionCount(int _sessionCount) {
        this.sessionCount = _sessionCount;
    }

    public int getSessionCount() {
        return this.sessionCount;
    }

    // set and get for performed
    public void setIdPerformed(int _id) {
        this.idPerformed = _id;
    }

    public int getIdPerformed() {
        return this.idPerformed;
    }

    public void setPerformedDate(String _performedDate) {
        this.performedDate = _performedDate;
    }

    public String getPerformedDate() {
        return this.performedDate;
    }

    public void setFromProgramId(int _programId) {
        this.fromProgramId = _programId;
    }

    public int getFromProgramId() {
        return this.fromProgramId;
    }

    public void setVolumes(double _volumes) {
        this.volumes = _volumes;
    }

    public double getVolumes() {
        return this.volumes;
    }

}

package com.litcode;

public class CodeResponse {
    private Object[] results, expected;
    private String returnType;
    private int exitCode;
    private int numOfResults;
    private String printedString, errorString;
    private boolean successful;

    public CodeResponse() {
        this.results = new Object[0];
        this.expected = new Object[0];
        this.returnType = "";
        this.exitCode = -1;
        this.numOfResults = 0;
        this.printedString = "";
        this.errorString = "";
        this.successful = false;
    }

    public CodeResponse(
        Object[] results, Object[] expected, String returnType, int exitCode, int numOfResults, String printString, String errorString, boolean successful) {
        this.results = results;
        this.expected = expected;
        this.returnType = returnType;
        this.exitCode = exitCode;
        this.numOfResults = numOfResults;
        this.printedString = printString;
        this.errorString = errorString;
        this.successful = successful;
    }

    public boolean[] compareResults() {
        boolean[] result = new boolean[this.numOfResults];
        for (int i = 0; i < this.numOfResults; i++) result[i] = results[i].equals(expected[i]);
        return result;
    }

    public boolean allResultsMatch() {
        for (boolean bool : compareResults())
            if (!bool)
                return false;
        return true;
    }

    @Override
    public String toString() {
        String resp = "\nCodeResponse:\n\tResults: [";
        for (Object obj : results) resp += obj.toString() + ", ";
        resp += "]\n\tExpected: [";
        for (Object obj : expected) resp += obj.toString() + ", ";
        resp += String.format("]\n\tPrints: \n%s", this.printedString);
        resp += String.format(" \n\tReturn type: %s,", this.returnType);
        resp += String.format(" \n\tExit code: %s,", String.valueOf(this.exitCode));
        resp += String.format(" \n\tNumber of results: %s,", String.valueOf(this.numOfResults));
        resp += String.format(" \n\tError string: %s%s", this.errorString.isEmpty() ? "No errors" : "\n", this.errorString);
        resp += String.format(" \n\tSuccess: %s,", this.successful ? "true" : "false");
        return resp;
    }

    public Object[] getResults() {
        return results;
    }

    public Object[] getExpected() {
        return expected;
    }

    public String getReturnType() {
        return returnType;
    }

    public int getExitCode() {
        return exitCode;
    }

    public int getNumOfResults() {
        return numOfResults;
    }

    public String getPrintedString() {
        return printedString;
    }

    public String getErrorString() {
        return errorString;
    }

    public boolean getSuccessful() {
        return successful;
    }
}

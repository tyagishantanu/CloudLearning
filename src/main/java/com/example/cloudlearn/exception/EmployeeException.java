package com.example.cloudlearn.exception;

public class EmployeeException extends RuntimeException{
	
	private static final long serialVersionUID = 7404337877919254997L;

	public EmployeeException(String message) {
        super(message);
    }

    public static class EmployeeNotFoundException extends EmployeeException {
        private static final long serialVersionUID = -8734701484021333548L;

		public EmployeeNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidEmployeeDataException extends EmployeeException {
        private static final long serialVersionUID = -1776187691421405404L;

		public InvalidEmployeeDataException(String message) {
            super(message);
        }
    }

    public static class InternalServerErrorException extends EmployeeException {
        private static final long serialVersionUID = 313650084642790943L;

		public InternalServerErrorException(String message) {
            super(message);
        }
    }

}

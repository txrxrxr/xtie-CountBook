/*
 * Counter
 *
 * Version 1.0
 *
 * September 21, 2017
 *
 */

package com.tiejun.countbook;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Represent a counter.
 */

public class Counter {

    private String name;
    private Date date;
    private int current;
    private int initial;
    private String comment;

    /**
     * Construct a counter object with parameters provided by uses, if invalid inputs given, throws exception.
     *
     * @param name the name of the counter
     * @param date the date of the counter
     * @param initial the initial value of the counter
     * @param comment the comment of the counter
     * @throws InvalidInputException the exception for invalid inputs
     */
    public Counter(String name, Date date, String initial, String comment) throws InvalidInputException{
        if (name.isEmpty() || initial.isEmpty())
            throw new InvalidInputException();
        else if (Integer.parseInt(initial) < 0)
            throw new InvalidInputException();

        this.name = name;
        this.date = date;
        this.initial = Integer.parseInt(initial);
        this.current = Integer.parseInt(initial);
        this.comment = comment;
    }

    /**
     * Set the name of the counter.
     *
     * @param name the name for the counter
     * @throws InvalidInputException the exception for invalid inputs
     */
    public void setName(String name) throws InvalidInputException{
        if (name.isEmpty())
            throw new InvalidInputException();
        this.name = name;
    }

    /**
     * Set the current value for the counter.
     *
     * @param current
     * @throws InvalidInputException
     */
    public void setCurrent(int current) throws InvalidInputException{
        if (current < 0)
            throw new InvalidInputException();
        this.current = current;
    }

    /**
     * Set the comment for the counter
     *
     * @param comment comment of the counter
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Set the date for the counter.
     *
     * @param date date of the counter
     */
    public void setDate (Date date) {
        this.date = date;
    }

    /**
     * Set the initial value of the counter
     *
     * @param initial initial value of the counter
     * @throws InvalidInputException
     */
    public void setInitial(int initial) throws  InvalidInputException {
        if (initial < 0)
            throw new InvalidInputException();
        this.initial = initial;
    }

    /**
     * Get the initial value of the counter
     *
     * @return int of initial value
     */
    public int resetValue() {
        return initial;
    }

    /**
     * Get the name of the counter
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get the current value of the counter
     *
     * @return
     */
    public int getCurrent() {
        return current;
    }

    /**
     * Get the date of the counter
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * Get the date of the counter
     *
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * Return the string representation of the counter object
     *
     * @return
     */
    @Override
    public String toString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(date) + "\n" + "Name: "+name + "\n"+"Current value: "+current;

    }
}

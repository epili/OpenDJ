/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyright [year] [name of copyright owner]".
 *
 * Copyright 2008 Sun Microsystems, Inc.
 * Portions Copyright 2016 ForgeRock AS.
 */
package org.forgerock.opendj.config.server;

import java.util.Collection;

import org.forgerock.i18n.LocalizableMessage;

/**
 * An interface for performing server-side constraint validation.
 * <p>
 * Constraints are evaluated immediately before and after write operations are
 * performed. Server-side constraints are evaluated in two phases: the first
 * phase determines if the proposed add, delete, or modification is acceptable
 * according to the constraint. If one or more constraints fails, the write
 * write operation is refused, and the client will receive an
 * <code>OperationRejectedException</code> exception. The second phase is
 * invoked once the add, delete, or modification request has been allowed and
 * any changes applied. The second phase gives the constraint handler a chance
 * to register listener call-backs if required.
 * <p>
 * A server constraint handler must override at least one of the provided
 * methods.
 *
 * @see org.forgerock.opendj.config.Constraint
 */
public abstract class ServerConstraintHandler {

    /** Creates a new server constraint handler. */
    protected ServerConstraintHandler() {
        // No implementation required.
    }

    /**
     * Determines whether the existing managed object can be deleted from
     * the server's configuration. For example, an implementation might enforce
     * referential integrity by preventing referenced managed objects from being
     * deleted.
     * <p>
     * If the constraint is not satisfied, the implementation must return
     * <code>false</code> and add a message describing why the managed object
     * cannot be deleted.
     * <p>
     * The default implementation is to return <code>true</code>.
     *
     * @param managedObject
     *            The managed object which is about to be deleted.
     * @param unacceptableReasons
     *            A list of messages to which error messages should be added.
     * @return Returns <code>true</code> if this constraint is satisfied, or
     *         <code>false</code> if it is not and the managed object cannot be
     *         deleted.
     * @throws ConfigException
     *             If an configuration exception prevented this constraint from
     *             being evaluated.
     */
    public boolean isDeleteAllowed(ServerManagedObject<?> managedObject,
            Collection<LocalizableMessage> unacceptableReasons) throws ConfigException {
        return true;
    }

    /**
     * Determines whether the provided managed object can be used by the
     * server. This method is invoked each time a managed object is decoded by
     * the administration framework: when an attempt is made to add a new
     * configuration, modify an existing configuration, or during server
     * initialization. If the constraint is not satisfied the managed object
     * will be rejected.
     * <p>
     * If the constraint is not satisfied, the implementation must return
     * <code>false</code> and add a message describing why the managed object is
     * not usable.
     * <p>
     * The default implementation is to return <code>true</code>.
     *
     * @param managedObject
     *            The new managed object.
     * @param unacceptableReasons
     *            A list of messages to which error messages should be added.
     * @return Returns <code>true</code> if this constraint is satisfied, or
     *         <code>false</code> if it is not and the managed object cannot be
     *         used.
     * @throws ConfigException
     *             If an configuration exception prevented this constraint from
     *             being evaluated.
     */
    public boolean isUsable(ServerManagedObject<?> managedObject, Collection<LocalizableMessage> unacceptableReasons)
            throws ConfigException {
        return true;
    }

    /**
     * Performs any post-add processing required by this constraint. This method
     * is invoked after a new managed object has been accepted for use by the
     * administration framework. This might occur during initialization or when
     * a managed object is added at run-time.
     * <p>
     * The default implementation is to do nothing.
     *
     * @param managedObject
     *            The managed object which has just been added to the server's
     *            configuration.
     * @throws ConfigException
     *             If the post-add processing fails due to a configuration
     *             exception.
     */
    public void performPostAdd(ServerManagedObject<?> managedObject) throws ConfigException {
        // Do nothing.
    }

    /**
     * Performs any post-delete processing required by this constraint. This
     * method is invoked after a managed object has been accepted for deletion
     * from the server's configuration.
     * <p>
     * The default implementation is to do nothing.
     *
     * @param managedObject
     *            The managed object which was deleted.
     * @throws ConfigException
     *             If the post-delete processing fails due to a configuration
     *             exception.
     */
    public void performPostDelete(ServerManagedObject<?> managedObject) throws ConfigException {
        // Do nothing.
    }

    /**
     * Performs any post-modify processing required by this constraint. This
     * method is invoked after changes to an existing managed object have been
     * accepted.
     * <p>
     * The default implementation is to do nothing.
     *
     * @param managedObject
     *            The managed object which was modified.
     * @throws ConfigException
     *             If the post-modify processing fails due to a configuration
     *             exception.
     */
    public void performPostModify(ServerManagedObject<?> managedObject) throws ConfigException {
        // Do nothing.
    }
}

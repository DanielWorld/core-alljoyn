/******************************************************************************
 * Copyright AllSeen Alliance. All rights reserved.
 *
 *    Permission to use, copy, modify, and/or distribute this software for any
 *    purpose with or without fee is hereby granted, provided that the above
 *    copyright notice and this permission notice appear in all copies.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 *    WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 *    MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 *    ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 *    WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 *    ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 *    OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 ******************************************************************************/

package org.alljoyn.about;

import java.util.List;

import org.alljoyn.about.client.AboutClient;
import org.alljoyn.about.icon.AboutIconClient;
import org.alljoyn.about.transport.AboutTransport;
import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusException;
import org.alljoyn.services.common.AnnouncementHandler;
import org.alljoyn.services.common.BusObjectDescription;
import org.alljoyn.services.common.PropertyStore;
import org.alljoyn.services.common.ServiceAvailabilityListener;
import org.alljoyn.services.common.ServiceCommon;

/**
 * An interface for both About client (consumer) and server (producer).
 * An application may want to implement both, but still use one bus, so for
 * convenience both functionalities are encapsulated here.
 *
 * @deprecated please see org.alljoyn.bus.AboutObj class and
 * org.alljoyn.bus.AboutIconObj class
 */
@Deprecated
public interface AboutService extends ServiceCommon
{
    /**
     * The About protocol version.
     * @deprecated please see org.alljoyn.bus.AboutObj class
     */
    @Deprecated
    public static final int PROTOCOL_VERSION = 1;

    /**
     * The About AllJoyn BusInterface name
     * @deprecated please see org.alljoyn.bus.AboutObj class
     */
    @Deprecated
    public static final String ANNOUNCE_IFNAME   = AboutTransport.INTERFACE_NAME;

    /**
     * The Announcement signal name
     * @deprecated please see org.alljoyn.bus.AboutObj class
     */
    @Deprecated
    public static final String SIGNAL_NAME       = "Announce";

    /**
     * Starts the {@link AboutService} in client mode.
     * @deprecated please see org.alljoyn.bus.AboutObj class
     * @param bus the AllJoyn bus attachment
     * @throws Exception Is thrown if a failure has occurred while starting the client
     */
    @Deprecated
    public void startAboutClient(BusAttachment bus) throws Exception;

    /**
     * Creates an About client for a peer to receive Announcement signals and
     * to call remote methods.
     * @deprecated please see org.alljoyn.bus.AboutObj class
     * @param peerName The bus unique name of the remote About server
     * @param serviceAvailabilityListener listener for connection loss
     * @param port the peer's bound port of the About server
     * @return AboutClient to create a session with the peer
     * @throws Exception indicating failure to create an AboutClient
     */
    @Deprecated
    public AboutClient createAboutClient(String peerName, ServiceAvailabilityListener serviceAvailabilityListener, short port) throws Exception;

    /**
     * Create an Icon client for a peer.
     * @deprecated please see org.alljoyn.bus.AboutIconObj class
     * @param peerName The bus unique name of the remote About server
     * @param serviceAvailabilityListener listener for connection loss
     * @param port the peer's bound port of the About server
     * @return AboutIconClient to create a session with the peer
     * @throws BusException indicating failure to creat an AboutIconClient
     */
    @Deprecated
    public AboutIconClient createAboutIconClient(String peerName, ServiceAvailabilityListener serviceAvailabilityListener, short port) throws BusException;

    /**
     * Stops client mode. Disconnect all sessions.
     * @deprecated please see org.alljoyn.bus.AboutObj class
     * @throws Exception indicating issue when stopping the AboutClient
     */
    @Deprecated
    public void stopAboutClient() throws Exception;

    /**
     * <p>Registers a handler to receive announcements.</p>
     *
     * <p>The handler is only called if all the interfaces are implemented.
     * For example, if the handler should be called if both "com.example.Audio"
     * <i>and</i> "com.example.Video" are implemented then call</p>
     * addAnnouncementHandler once:
     * <pre>
     * {@code
     * addAnnouncementHandler(handler, new String[] {"com.example.Audio", "com.example.Video"});
     * }</pre>
     *
     * <p>If the handler should be called if "com.example.Audio" <i>or</i>
     * "com.example.Video" is implemented then call</p>
     * addAnnouncementHandler multiple times:
     * <pre>
     * {@code
     * addAnnouncementHandler(handler, new String[] {"com.example.Audio"});
     * addAnnouncementHandler(handler, new String[] {"com.example.Video"});
     * }</pre>
     *
     * <p>The interface name may be a prefix followed by a <code>*</code>.  Using
     * this, the example above could be written as:</p>
     * <pre>
     * {@code
     * addAnnouncementHandler(handler, new String[] {"com.example.*"});
     * }</pre>
     * <p>The handler will receive any announcement that implements an interface
     * beginning with the "com.example." name.</p>
     *
     * <p>If RegisterAnnounceHandler is called with lists of interfaces that overlap
     * then multiple Announcements will occur.</p>
     * For example given the following:
     * <pre>
     * {@code
     * addAnnouncementHandler(handler, new String[] {"com.example.Audio"});
     * addAnnouncementHandler(handler, new String[] {"com.example.Audio", "com.example.Video"});
     * }</pre>
     *
     * <p>If a BusAttachment is found that implements both `com.example.Audio`  and
     * interface `com.example.Video` the announce handler will be called twice.
     * Once for the first added AnnouncementHandler for `com.example.Audio` and
     * again for the second added AnnouncementHandler that is looking for both
     * audio and video interfaces.</p>
     *
     * <p>If the same handler is used for for multiple interfaces then it is the
     * handlers responsibility to parse through the reported interfaces to
     * figure out what should be done in response to the Announce signal.</p>
     *
     * <p><em>Note:</em> specifying null for the interfaces parameter could have
     * significant impact on network performance and should be avoided unless
     * all announcements are needed.</p>
     * @deprecated please see org.alljoyn.bus.BusAttachment.registerAboutListener
     * and org.alljoyn.bus.BusAttachment.whoImplements
     * @param handler the AnnouncementHandler that will be receiving the
     *                announcement.
     * @param interfaces an array of interfaces the remote service must
     *                   implement to receive the announcement.  If this
     *                   parameter is <code>null</code> the handler will receive
     *                   all announcements.
     *
     * @see AboutTransport#Announce(short, short, BusObjectDescription[], java.util.Map)
     */
    @Deprecated
    public void addAnnouncementHandler(AnnouncementHandler handler, String[] interfaces);

    /**
     * Unregisters an announcement handler.
     *
     * When calling this the array of interfaces must match the array that was
     * used when calling <code>addAnnouncementHander</code>.
     *
     * @deprecated please see org.alljoyn.bus.BusAttachment.unregisterAboutListener
     * and org.alljoyn.bus.BusAttachment.cancelWhoImplements
     * @param handler the AnnouncementHandler to remove. The array of interfaces
     *                must be the same as the array used when calling
     *                addAnnouncementHandler.
     * @param interfaces an array of interfaces that were used when calling
     *                   addAnnouncementHandler
     */
    @Deprecated
    public void removeAnnouncementHandler(AnnouncementHandler handler, String[] interfaces);

    /**
     * Start server mode.  The application creates the BusAttachment
     * @deprecated please see org.alljoyn.bus.AboutObj class
     * @param port the bound AllJoyn session port. The application binds the port,
     *             and the about server announces it.
     * @param propertyStore a container of device/application properties.
     * @param m_bus the AllJoyn bus attachment.
     * @throws Exception indicating failure when starting the AboutServer
     * @see AboutKeys
     */
    @Deprecated
    public void startAboutServer(short port, PropertyStore propertyStore, BusAttachment m_bus) throws Exception;

    /**
     * Stop server mode.
     * @deprecated please see org.alljoyn.bus.AboutObj class
     */
    @Deprecated
    public void stopAboutServer();

    /**
     * Add a BusObject and the BusInterfaces that it implements, to the server's
     * Announcement.
     * @deprecated please see org.alljoyn.bus.BusAttachment.registerBusObject class,
     * org.alljoyn.bus.BusAttachment.setAnnounceFlag,
     * and org.alljoyn.bus.annotation.BusInterface.announced
     * @param objPath the path of the BusObject
     * @param interfaces the BusInterfaces that the BusObject implements.
     * @see BusObjectDescription
     * @see AboutTransport#Announce(short, short, BusObjectDescription[], java.util.Map)
     */
    @Deprecated
    public void addObjectDescription(String objPath, String [] interfaces);

    /**
     * Remove the BusInterfaces that the given BusObject implements, from the
     * server's Announcement
     * @deprecated please see org.alljoyn.bus.BusAttachment.unregisterBusObject class,
     * and org.alljoyn.bus.BusAttachment.setAnnounceFlag.
     * @param objPath the path of the BusObject
     * @param interfaces the interfaces to remove.
     *                   Note: only the passed interfaces will be removed.
     */
    @Deprecated
    public void removeObjectDescription(String objPath, String [] interfaces);

    /**
     * A plural call of addBusObjectDescription
     * @deprecated please see org.alljoyn.bus.BusAttachment.registerBusObject class,
     * org.alljoyn.bus.BusAttachment.setAnnounceFlag,
     * and org.alljoyn.bus.annotation.BusInterface.announced
     * @param addBusObjectDescriptions list of BusObjectDescriptions to add to the next Announce signal
     * @see #addObjectDescription(String, String[])
     */
    @Deprecated
    public void addObjectDescriptions(List<BusObjectDescription> addBusObjectDescriptions);

    /**
     * A plural call of removeBusObjectDescription
     * @deprecated please see org.alljoyn.bus.BusAttachment.unregisterBusObject class,
     * and org.alljoyn.bus.BusAttachment.setAnnounceFlag.
     * @param removeBusObjectDescriptions list of BusObjectDescriptions to remove from next the Announce signal
     * @see #removeObjectDescription(String, String[])
     */
    @Deprecated
    public void removeObjectDescriptions(List<BusObjectDescription> removeBusObjectDescriptions);

    /**
     * Make the server send an Announcement
     * @deprecated please see org.alljoyn.bus.BusAttachment.announce
     */
    @Deprecated
    public void announce();

    /**
     * Register the application icon with the server
     *
     * @deprecated please see org.alljoyn.bus.AboutIconObj
     * @param mimetype the MimeType for the image
     * @param url  A URL that contains the location of the icon hosted in the cloud.
     * @param content an array of bytes that represent an icon
     * @throws Exception indicating failure to register the icon.
     */
    @Deprecated
    public void registerIcon(String mimetype, String url, byte[] content) throws Exception;

    /**
     * Unregister the application icon
     * @deprecated please see org.alljoyn.bus.AboutIconObj
     */
    @Deprecated
    public void unregisterIcon();
}

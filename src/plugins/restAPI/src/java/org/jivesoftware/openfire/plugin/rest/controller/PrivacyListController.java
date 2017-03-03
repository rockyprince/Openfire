package org.jivesoftware.openfire.plugin.rest.controller;

import javax.ws.rs.core.Response;

import org.jivesoftware.openfire.privacy.PrivacyList;
import org.jivesoftware.openfire.privacy.PrivacyListManager;
import org.jivesoftware.openfire.privacy.PrivacyListProvider;
import org.jivesoftware.openfire.plugin.rest.entity.PrivacyListItem;
import org.jivesoftware.openfire.plugin.rest.exceptions.ExceptionType;
import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;
import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

/**
 * The Class PrivacyListController. //rockyprince, the file
 */
public class PrivacyListController {
	/** The Constant INSTANCE. */
	public static final PrivacyListController INSTANCE = new PrivacyListController();

	/** The privacy list manager. */
	private PrivacyListManager privacyListManager;
        
	/** The privacy list provider. */
	private PrivacyListProvider privacyListProvider;        
	
	/**
	 * Gets the single instance of PrivacyListController.
	 *
	 * @return single instance of PrivacyListController
	 */
	public static PrivacyListController getInstance() {
		return INSTANCE;
	}
        
	/**
	 * Instantiates a new privacy list controller.
	 */
	private PrivacyListController() {
		privacyListManager = PrivacyListManager.getInstance();
                privacyListProvider = new PrivacyListProvider();
	}
        
	/**
	 * Creates the privacy list.
	 *
	 * @param username
	 *            the username
	 * @param listname
	 *            the privacy list name
	 * @param privacyListItem
	 *            the privacy list item
	 * @throws ServiceException
	 *             the service exception
	 */
	public void createPrivacyLists(String username, String listname, PrivacyListItem privacyListItem) throws ServiceException {
		if (username != null && !username.isEmpty() && listname != null && !listname.isEmpty() && privacyListItem != null) {
			if (privacyListItem.getType() == null || privacyListItem.getType().isEmpty()
                                || privacyListItem.getValue() == null || privacyListItem.getValue().isEmpty()
                                || privacyListItem.getAction() == null || privacyListItem.getAction().isEmpty()
                                || privacyListItem.getOrder() == null || privacyListItem.getOrder().isEmpty()
                                || privacyListItem.getBlockType() == null) {
				throw new ServiceException("Could not create new privacy list, because item type or value or action or order or blockType is null",
						username, "PrivacyListIsNull", Response.Status.BAD_REQUEST);
			}
			try {
                                Document document = DocumentHelper.createDocument();
//                                Element listElement = document.addElement("list", "jabber:iq:privacy");
//                                listElement.addAttribute("name", name);
//                                        for (PrivacyListItem item : privacyListEntity.getItems()) {
                                                Element itemElement = document.addElement("item", "");
                                                itemElement.addAttribute("type", privacyListItem.getType());
                                                itemElement.addAttribute("value", privacyListItem.getValue());
                                                itemElement.addAttribute("action", privacyListItem.getAction());
                                                itemElement.addAttribute("order", privacyListItem.getOrder());
                                                for (String blockType : privacyListItem.getBlockType()) {
                                                    if ("1".equals(blockType)) {
                                                        itemElement.addElement("message", "");
                                                    } else if ("2".equals(blockType)) {
                                                        itemElement.addElement("presence-in", "");
                                                    } else if ("3".equals(blockType)) {
                                                        itemElement.addElement("presence-out", "");
                                                    } else if ("4".equals(blockType)) {
                                                        itemElement.addElement("iq", "");
                                                    }
                                                }
//                                        }
                                        PrivacyList list = privacyListManager.getPrivacyList(username, listname);
                                        if ( list != null) {
//                                                privacyListManager.deletePrivacyList(username, privacyListEntity.getName());
                                                Element listElement = list.asElement();
                                                Element item = (Element) listElement.selectSingleNode("//item[@value='" + privacyListItem.getValue() + "']");
                                                if (item != null) {
                                                        throw new ServiceException("Could not create privacy list", username,
                                                                        ExceptionType.ALREADY_EXISTS, Response.Status.CONFLICT);
                                                }
                                                listElement.add(itemElement);
                                                list.updateList(listElement);
                                                privacyListProvider.updatePrivacyList(username, list);
                                        } else {
                                                list = new PrivacyList();
                                                Element listElement = list.asElement();
                                                listElement.add(itemElement);
                                                list = privacyListManager.createPrivacyList(username, listname, listElement);
                                                if (listname == "blacklist"){
                                                    privacyListManager.changeDefaultList(username, list, null);
                                                }
                                        }
			} catch (Exception e) {
                                e.printStackTrace();
				throw new ServiceException("Could not create privacy list", username,
						e.toString(), Response.Status.CONFLICT);
			}
		} else {
			throw new ServiceException("Could not create new privacy list",
					"PrivacyLists", ExceptionType.ILLEGAL_ARGUMENT_EXCEPTION, Response.Status.BAD_REQUEST);
		}
	}
        
	/**
	 * Delete the privacy list.
	 *
	 * @param username
	 *            the username
	 * @param listname
	 *            the privacy list name
	 * @param privacyListItem
	 *            the privacy list item
	 * @throws ServiceException
	 *             the service exception
	 */
	public void deletePrivacyLists(String username, String listname, PrivacyListItem privacyListItem) throws ServiceException {
		if (username != null && !username.isEmpty() && listname != null && !listname.isEmpty() && privacyListItem != null) {
			if (privacyListItem.getType() == null || privacyListItem.getType().isEmpty()
                                || privacyListItem.getValue() == null || privacyListItem.getValue().isEmpty()
                                || privacyListItem.getAction() == null || privacyListItem.getAction().isEmpty()
                                || privacyListItem.getOrder() == null || privacyListItem.getOrder().isEmpty()
                                || privacyListItem.getBlockType() == null) {
				throw new ServiceException("Could not delete privacy list, because item type or value or action or order or blockType is null",
						username, "PrivacyListIsNull", Response.Status.BAD_REQUEST);
			}
			try {
                                Document document = DocumentHelper.createDocument();
//                                Element listElement = document.addElement("list", "jabber:iq:privacy");
//                                listElement.addAttribute("name", name);
//                                        for (PrivacyListItem item : privacyListEntity.getItems()) {
                                                Element itemElement = document.addElement("item", "");
                                                itemElement.addAttribute("type", privacyListItem.getType());
                                                itemElement.addAttribute("value", privacyListItem.getValue());
                                                itemElement.addAttribute("action", privacyListItem.getAction());
                                                itemElement.addAttribute("order", privacyListItem.getOrder());
                                                for (String blockType : privacyListItem.getBlockType()) {
                                                    if ("1".equals(blockType)) {
                                                        itemElement.addElement("message", "");
                                                    } else if ("2".equals(blockType)) {
                                                        itemElement.addElement("presence-in", "");
                                                    } else if ("3".equals(blockType)) {
                                                        itemElement.addElement("presence-out", "");
                                                    } else if ("4".equals(blockType)) {
                                                        itemElement.addElement("iq", "");
                                                    }
                                                }
//                                        }
                                        PrivacyList list = privacyListManager.getPrivacyList(username, listname);
                                        if ( list != null) {
//                                                privacyListManager.deletePrivacyList(username, privacyListEntity.getName());
                                                Element listElement = list.asElement();
                                                Element item = (Element) listElement.selectSingleNode("//item[@value='" + privacyListItem.getValue() + "']");
                                                item.detach();
                                                list.updateList(listElement);
                                                privacyListProvider.updatePrivacyList(username, list);
                                        }
			} catch (Exception e) {
                                e.printStackTrace();
				throw new ServiceException("Could not delete privacy list", username,
						ExceptionType.PRIVACY_LIST_ITEM_NOT_FOUND, Response.Status.NOT_FOUND);
			}
		} else {
			throw new ServiceException("Could not delete new privacy list",
					"PrivacyLists", ExceptionType.ILLEGAL_ARGUMENT_EXCEPTION, Response.Status.BAD_REQUEST);
		}
	}
        
        
}
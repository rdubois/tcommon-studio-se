// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.ui.properties.tab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchPart;

/**
 * DOC yzhang class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40Z nrousseau $
 * 
 */
public class TalendTabbedPropertyViewer extends StructuredViewer implements IInputChangedListener {

    protected TalendTabbedPropertyList list;

    protected List elements;

    protected IWorkbenchPart part;

    /**
     * Constructor for TabbedPropertyViewer.
     * 
     * @param list the TabbedPropertyList.
     */
    public TalendTabbedPropertyViewer(TalendTabbedPropertyList list) {
        this.list = list;
        this.list.addInputChangedListener(this);
        hookControl(list);
        elements = new ArrayList();
    }

    /**
     * Returns the element with the given index from this list viewer. Returns <code>null</code> if the index is out
     * of range.
     * 
     * @param index the zero-based index
     * @return the element at the given index, or <code>null</code> if the index is out of range
     */
    public Object getElementAt(int index) {
        if (index >= 0 && index < elements.size()) {
            return elements.get(index);
        }
        return null;
    }

    /**
     * Returns the zero-relative index of the item which is currently selected in the receiver, or -1 if no item is
     * selected.
     * 
     * @return the index of the selected item
     */
    public int getSelectionIndex() {
        return list.getSelectionIndex();
    }

    protected Widget doFindInputItem(Object element) {
        /* not implemented */
        return null;
    }

    protected Widget doFindItem(Object element) {
        /* not implemented */
        return null;
    }

    protected void doUpdateItem(Widget item, Object element, boolean fullMap) {
        /* not implemented */
    }

    protected List getSelectionFromWidget() {
        int index = list.getSelectionIndex();
        if (index == TalendTabbedPropertyList.NONE) {
            return Collections.EMPTY_LIST;
        }
        List result = new ArrayList(1);
        result.add(getElementAt(index));
        return result;
    }

    protected void internalRefresh(Object element) {
        /* not implemented */
    }

    public void reveal(Object element) {
        /* not implemented */
    }

    /**
     * We do not consider multiple selections. Only the first element will represent the selection.
     */
    protected void setSelectionToWidget(List l, boolean reveal) {
        if (l == null || l.size() == 0) { // clear selection
            list.deselectAll();
        } else {
            Object object = l.get(0);
            int index = -1;
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i) == object) {
                    index = i;
                }
            }
            Assert.isTrue(index != -1, "Could not set the selected tab in the tabbed property viewer");//$NON-NLS-1$
            list.select(index);
        }
    }

    public Control getControl() {
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.Viewer#inputChanged(java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Object input, Object oldInput) {
        elements.clear();
        Object[] children = getSortedChildren(getRoot());
        list.removeAll();
        for (int i = 0; i < children.length; i++) {
            elements.add(children[i]);
            mapElement(children[i], list);
        }
        list.setElements(children);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.properties.tab.IInputChangedListener#inputChanged(java.lang.Object)
     */
    public void inputChanged(TabInputChangedEvent event) {
        setInput(event.getInput());
        IStructuredSelection selection = (IStructuredSelection) getSelection();
        if (selection.getFirstElement() == null) {
            setSelection(event.getDefaultSelection());
        }

    }

    /**
     * Set the input for viewer.
     * 
     * @param part the workbench part.
     * @param selection the selection in the workbench part.
     */
    public void setInput(IWorkbenchPart part, ISelection selection) {
        this.part = part;
        setInput(selection);
    }

    /**
     * Get the current workbench part.
     * 
     * @return the current workbench part.
     */
    public IWorkbenchPart getWorkbenchPart() {
        return part;
    }
}

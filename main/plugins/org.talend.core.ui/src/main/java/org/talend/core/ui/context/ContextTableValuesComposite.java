// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.ui.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.talend.commons.ui.runtime.image.ECoreImage;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.tooltip.AbstractTreeTooltip;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.language.LanguageManager;
import org.talend.core.model.context.ContextUtils;
import org.talend.core.model.context.JobContextManager;
import org.talend.core.model.context.JobContextParameter;
import org.talend.core.model.metadata.MetadataTalendType;
import org.talend.core.model.metadata.types.ContextParameterJavaTypeManager;
import org.talend.core.model.metadata.types.PerlTypesManager;
import org.talend.core.model.process.IContext;
import org.talend.core.model.process.IContextManager;
import org.talend.core.model.process.IContextParameter;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.core.runtime.i18n.Messages;
import org.talend.core.ui.context.model.ContextTabChildModel;
import org.talend.core.ui.context.model.ContextValueErrorChecker;
import org.talend.core.ui.context.model.ContextViewerProvider;
import org.talend.core.ui.context.model.table.ContextTableCellModifier;
import org.talend.core.ui.context.model.table.ContextTableConstants;
import org.talend.core.ui.context.model.table.ContextTableTabChildModel;
import org.talend.core.ui.context.model.table.ContextTableTabParentModel;
import org.talend.core.ui.context.model.table.GroupByNothingTableProvider;
import org.talend.core.ui.context.model.table.GroupBySourceTableProvider;
import org.talend.core.ui.context.model.template.ContextViewerSorter;
import org.talend.core.utils.TalendQuoteUtils;

/**
 * DOC zwang class global comment. Detailled comment <br/>
 * 
 */
public class ContextTableValuesComposite extends AbstractContextTabEditComposite {

    public static final int CONTEXT_COLUMN_WIDTH = 200;

    public static final String NEW_PARAM_NAME = "new"; //$NON-NLS-1$

    private TreeViewer viewer;

    private ContextViewerProvider provider;

    private IContextModelManager modelManager = null;

    private ContextTableCellModifier cellModifier;

    private DefaultCellEditorFactory cellFactory;

    private ConfigureContextAction configContext;

    private Combo contextsCombo;

    private Button contextConfigButton;

    private CellEditor[] cellEditors;

    private ContextValueErrorChecker valueChecker;

    private static final int VALUES_INDEX = 1;

    private ContextManagerHelper helper;

    private List<Button> buttonList;

    private Listener sortListener;

    private Button moveDownButton;

    private Button moveUpButton;

    private Button orderButton;

    private Button removeButton;

    /**
     * Constructor.
     * 
     * @param parent
     * @param style
     */
    public ContextTableValuesComposite(Composite parent, IContextModelManager manager) {
        super(parent, SWT.NONE);
        modelManager = manager;
        cellFactory = new DefaultCellEditorFactory(this);
        buttonList = new ArrayList<Button>();
        this.helper = new ContextManagerHelper(manager.getContextManager());
        this.setBackground(parent.getBackground());
        this.setLayout(GridLayoutFactory.swtDefaults().spacing(0, 0).create());
        initializeUI();
    }

    public IContextManager getContextManager() {
        return modelManager.getContextManager();
    }

    /**
     * zwang Comment method "initializeUI".
     * 
     * @param viewer
     */
    private void initializeUI() {
        // final ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.NO_BACKGROUND);
        // GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.TOP).applyTo(toolBar);

        createContextsGroup(this);

        if (modelManager.getProcess() != null && modelManager.getProcess() instanceof IProcess2) {
            modelManager.getProcess().getContextManager().getListContext();
        }

        viewer = new TreeViewer(this, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        Tree tree = viewer.getTree();
        tree.setHeaderVisible(true);
        tree.setLinesVisible(true);
        tree.setLayoutData(new GridData(GridData.FILL_BOTH));

        createColumnsAndCellEditors(tree, getContexts());
        tree.layout();

        boolean isRepositoryContext = (modelManager instanceof ContextComposite)
                && ((ContextComposite) modelManager).isRepositoryContext();
        cellModifier = new ContextTableCellModifier(this, isRepositoryContext);
        viewer.setCellModifier(cellModifier);

        provider = new ContextViewerProvider();
        changeContextProvider();

        final TreeEditor treeEditor = new TreeEditor(viewer.getTree());

        viewer.addPostSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                checkButtonEnableState();
            }
        });

        // viewer.getTree().addMouseListener(new MouseAdapter() {
        //
        // @Override
        // public void mouseDown(MouseEvent e) {
        // if (modelManager.isReadOnly()) {
        // return;
        // }
        // Point pt = new Point(e.x, e.y);
        // if (e.x > 0 && e.x < (viewer.getTree().getColumnCount()) * ContextTableValuesComposite.CONTEXT_COLUMN_WIDTH)
        // {
        // createEditorListener(treeEditor, e.x / CONTEXT_COLUMN_WIDTH);
        // }
        // TreeItem item = viewer.getTree().getItem(pt);
        // // deactivate the current cell editor
        // if (cellEditor != null && !cellEditor.getControl().isDisposed()) {
        // deactivateCellEditor(treeEditor, e.x / CONTEXT_COLUMN_WIDTH);
        // }
        // if (item != null && !item.isDisposed()) {
        // Rectangle rect = item.getBounds(viewer.getTree().getColumnCount() - 1);
        //
        // if (e.x > 0 && e.x < (viewer.getTree().getColumnCount()) * ContextTableValuesComposite.CONTEXT_COLUMN_WIDTH)
        // {
        // handleSelect(item, viewer.getTree(), treeEditor, viewer.getTree().getColumnCount() - 1, e.x
        // / CONTEXT_COLUMN_WIDTH);
        // }
        // }
        //
        // }
        // });
        valueChecker = new ContextValueErrorChecker(viewer);
        if (LanguageManager.getCurrentLanguage() == ECodeLanguage.PERL) {
            createTreeTooltip(tree);
        }

        final Composite buttonsComposite = new Composite(this, SWT.NONE);
        buttonsComposite.setLayout(GridLayoutFactory.swtDefaults().spacing(0, 0).margins(0, 0).numColumns(7).create());
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.DOWN).grab(true, false).applyTo(buttonsComposite);
        buttonList.clear();
        Button addButton = createAddPushButton(buttonsComposite);
        buttonList.add(addButton);
        removeButton = createRemovePushButton(buttonsComposite);
        buttonList.add(removeButton);

        if (!isRepositoryContext) {// for bug 7393
            moveUpButton = createMoveUpPushButton(buttonsComposite);
            buttonList.add(moveUpButton);
            moveDownButton = createMoveDownPushButton(buttonsComposite);
            buttonList.add(moveDownButton);
        }

        if ((modelManager instanceof ContextComposite) && !((ContextComposite) modelManager).isRepositoryContext()) {
            Button selectContextVariablesButton = createSelectContextVariablesPushButton(buttonsComposite);
            buttonList.add(selectContextVariablesButton);
        }
        if (!isRepositoryContext) {// for bug 7393
            orderButton = createOrderCheckButton(buttonsComposite);
            buttonList.add(orderButton);
        }
    }

    private Button createAddPushButton(final Composite parent) {
        Button addPushButton = new Button(parent, SWT.PUSH);
        addPushButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                IContextParameter parameter = (IContextParameter) createNewEntry();
                if (parameter != null) {
                    // set the source to built-in
                    parameter.setSource(IContextParameter.BUILT_IN);
                    modelManager.onContextAddParameter(getContextManager(), parameter);
                    ContextManagerHelper.revertTreeSelection(getViewer(), parameter);
                    checkButtonEnableState();

                    // see feature 4661: Add an option to propagate when add or remove a variable in a repository
                    // context to jobs/joblets.
                    if (ContextUtils.isPropagateContextVariable() && getContextManager() != null) {
                        IContextManager manager = getContextManager();
                        if (manager != null && manager instanceof JobContextManager) {
                            JobContextManager jobManger = (JobContextManager) manager;
                            // set updated flag.
                            jobManger.setModified(true);
                            jobManger.addNewParameters(parameter.getName());
                        }
                    }

                }
            }

        });
        Image image = ImageProvider.getImage(EImage.ADD_ICON);
        addPushButton.setImage(image);
        return addPushButton;
    }

    /**
     * cli Comment method "changeContextProvider".
     */
    private void changeContextProvider() {
        boolean groupBySource = false;
        IPreferenceStore preferenceStore = getPreferenceStore();
        if (preferenceStore != null) {
            groupBySource = preferenceStore.getBoolean(ITalendCorePrefConstants.CONTEXT_GROUP_BY_SOURCE);
        }
        if (groupBySource) {
            provider.setProvider(new GroupBySourceTableProvider(this));
        } else {
            provider.setProvider(new GroupByNothingTableProvider(this));
        }

        viewer.setLabelProvider(provider);
        viewer.setContentProvider(provider);
        addSorter(viewer);
    }

    /**
     * bqian Comment method "createTreeTooltip".
     * 
     * @param tree
     */
    protected void createTreeTooltip(final Tree tree) {
        new AbstractTreeTooltip(tree) {

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.talend.commons.ui.swt.tooltip.AbstractTreeTooltip#getTooltipContent(org.eclipse.swt.widgets.TreeItem)
             */
            @Override
            public String getTooltipContent(TreeItem item) {

                String property = ""; //$NON-NLS-1$
                if (properties != null && properties.length > VALUES_INDEX) {
                    property = properties[VALUES_INDEX];
                }

                IContextParameter para = cellModifier.getRealParameter(property, item.getData());
                if (para.getType().equalsIgnoreCase(PerlTypesManager.STRING)) {
                    return Messages.getString("PromptDialog.stringTip"); //$NON-NLS-1$
                }

                return null;
            }
        };
    }

    public Object createNewEntry() {
        List<IContextParameter> listParams = getContextManager().getDefaultContext().getContextParameterList();
        Integer numParam = new Integer(1);
        boolean paramNameFound;
        String paramName = null;
        do { // look for a new name
            paramNameFound = true;
            paramName = NEW_PARAM_NAME + numParam;
            for (int i = 0; i < listParams.size(); i++) {
                if (paramName.equals(listParams.get(i).getName())) {
                    paramNameFound = false;
                }
            }
            if (!paramNameFound) {
                numParam++;
            }
        } while (!paramNameFound);

        JobContextParameter contextParam = new JobContextParameter();
        contextParam.setName(paramName);
        ECodeLanguage curLanguage = LanguageManager.getCurrentLanguage();
        if (curLanguage == ECodeLanguage.JAVA) {
            contextParam.setType(ContextParameterJavaTypeManager.getDefaultJavaType().getId());
        } else {
            contextParam.setType(MetadataTalendType.getDefaultTalendType());
        }
        contextParam.setPrompt(paramName + "?"); //$NON-NLS-1$
        String defaultValue;
        if (curLanguage == ECodeLanguage.JAVA) {
            defaultValue = ContextParameterJavaTypeManager.getDefaultValueFromJavaIdType(ContextParameterJavaTypeManager
                    .getDefaultJavaType().getId(), false);
        } else {
            defaultValue = TalendQuoteUtils.addQuotes(""); //$NON-NLS-1$
        }
        contextParam.setValue(defaultValue);
        contextParam.setComment(""); //$NON-NLS-1$
        contextParam.setSource(""); //$NON-NLS-1$
        return contextParam;
    }

    private Button createRemovePushButton(final Composite parent) {
        Button removePushButton = new Button(parent, SWT.PUSH);
        removePushButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                // IContextParameter parameter = null;
                TreeItem[] items = viewer.getTree().getSelection();

                Object[] obj = new Object[items.length];

                for (int i = 0; i < obj.length; i++) {
                    obj[i] = items[i].getData();
                }

                if (items.length > 0) {
                    for (Object object : obj) { // multi delete
                        IContextParameter beforeParam = ContextManagerHelper.getNearParameterBySelectionItem(getViewer());
                        if (object == null) {
                            return;
                        }
                        if (isGroupBySource()) {
                            if (object instanceof ContextTableTabParentModel) {
                                ContextTableTabParentModel parentModel = (ContextTableTabParentModel) object;
                                removeParentModelInGroupBySource(parentModel);
                            } else if (object instanceof ContextTableTabChildModel) {
                                ContextTableTabChildModel childModel = (ContextTableTabChildModel) object;
                                removeChildModelInGroupBySource(childModel);
                            }
                        } else {
                            if (object instanceof ContextTableTabParentModel) {
                                ContextTableTabParentModel variableParentModel = (ContextTableTabParentModel) object;
                                removeParentModelNonGroupBySource(variableParentModel);
                            }
                        }
                        // modelManager.refreshTemplateTab();
                        modelManager.refreshTableTab();
                        ContextManagerHelper.revertTreeSelection(getViewer(), beforeParam);
                        checkButtonEnableState();
                    }
                }
            }
        });

        Image image = ImageProvider.getImage(EImage.DELETE_ICON);
        removePushButton.setImage(image);
        return removePushButton;
    }

    private void removeChildModelInGroupBySource(ContextTableTabChildModel child) {
        IContextParameter contextPara = child.getContextParameter();
        String sourceId = contextPara.getSource();
        String contextName = contextPara.getName();
        modelManager.onContextRemoveParameter(getContextManager(), contextName, sourceId);
    }

    private void removeParentModelInGroupBySource(ContextTableTabParentModel parentModel) {
        Set<String> paraNames = new HashSet<String>();
        String sourceId = parentModel.getSourceId();
        if (IContextParameter.BUILT_IN.equals(sourceId)) {
            String paraName = parentModel.getContextParameter().getName();
            paraNames.add(paraName);
        } else {
            List<ContextTabChildModel> children = parentModel.getChildren();
            if (children != null && children.size() > 0) {
                for (ContextTabChildModel child : children) {
                    IContextParameter contextPara = child.getContextParameter();
                    String paraName = contextPara.getName();
                    paraNames.add(paraName);
                }
            }
        }
        modelManager.onContextRemoveParameter(getContextManager(), paraNames, sourceId);
    }

    private void removeParentModelNonGroupBySource(ContextTableTabParentModel parentModel) {
        IContextParameter contextPara = parentModel.getContextParameter();
        String sourceId = contextPara.getSource();
        String paraName = contextPara.getName();

        modelManager.onContextRemoveParameter(getContextManager(), paraName, sourceId);
    }

    private Button createSelectContextVariablesPushButton(final Composite parent) {
        Button selectContextVariablesPushButton = new Button(parent, SWT.PUSH);
        Image image = ImageProvider.getImage(ECoreImage.CONTEXT_ICON);
        selectContextVariablesPushButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                SelectRepositoryContextDialog dialog = new SelectRepositoryContextDialog(getContextModelManager(), parent
                        .getShell(), helper);
                dialog.open();
                refresh();
            }

        });
        selectContextVariablesPushButton.setImage(image);
        return selectContextVariablesPushButton;
    }

    private Button createMoveUpPushButton(final Composite parent) {
        Button moveUpPushButton = new Button(parent, SWT.PUSH);
        Image image = ImageProvider.getImage(EImage.UP_ICON);
        moveUpPushButton.setImage(image);
        moveUpPushButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (ContextManagerHelper.changeContextOrder(viewer, modelManager, true)) {
                    checkButtonEnableState();
                    viewer.getTree().setFocus();
                }
            }

        });
        return moveUpPushButton;
    }

    private Button createMoveDownPushButton(final Composite parent) {
        Button moveDownPushButton = new Button(parent, SWT.PUSH);
        Image image = ImageProvider.getImage(EImage.DOWN_ICON);
        moveDownPushButton.setImage(image);
        moveDownPushButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (ContextManagerHelper.changeContextOrder(viewer, modelManager, false)) {
                    checkButtonEnableState();
                    viewer.getTree().setFocus();
                }
            }

        });
        return moveDownPushButton;
    }

    private void removeSorter(final TreeViewer viewer2) {
        final Tree table = viewer2.getTree();
        if (sortListener != null) {
            table.getColumn(0).removeListener(SWT.Selection, sortListener);
            if ((modelManager instanceof ContextComposite) && !((ContextComposite) modelManager).isRepositoryContext()) {
                table.getColumn(1).removeListener(SWT.Selection, sortListener);
            }
        }
        // re-order to original orders
        table.setSortDirection(SWT.NONE);
        viewer2.setSorter(new ContextViewerSorter(getContextManager()));

    }

    private Button createOrderCheckButton(final Composite parent) {
        Composite orderComposite = new Composite(parent, SWT.NONE);
        orderComposite.setBackground(parent.getBackground());

        orderComposite.setLayout(GridLayoutFactory.swtDefaults().spacing(10, 0).margins(5, 0).create());
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(orderComposite);

        final Button orderBtn = new Button(orderComposite, SWT.CHECK);
        orderBtn.setText(Messages.getString("ConextTemplateComposite.OrderText")); //$NON-NLS-1$
        orderBtn.setToolTipText(Messages.getString("ConextTemplateComposite.OrderMessages")); //$NON-NLS-1$
        orderBtn.setAlignment(SWT.LEFT);
        GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, false).applyTo(orderBtn);

        orderBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (orderBtn.getSelection()) {
                    removeSorter(viewer);
                } else {
                    addSorter(viewer);
                }
                refresh();
            }

        });
        return orderBtn;
    }

    private void checkButtonEnableState() {
        boolean orderEnable = false;
        boolean selectionEnable = false;
        boolean removeEnable = false;
        if (this.orderButton != null) {
            orderEnable = this.orderButton.getSelection();
        }
        if (this.viewer != null) {
            ISelection selection = this.viewer.getSelection();
            selectionEnable = !selection.isEmpty();
            removeEnable = !selection.isEmpty();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection sel = (IStructuredSelection) selection;
                if (sel.size() > 1) {
                    // Multi selection, not support sort
                    selectionEnable = false;
                }
            }
        }
        selectionEnable = selectionEnable && !modelManager.isReadOnly();
        removeEnable = removeEnable && !modelManager.isReadOnly();
        boolean moveState = orderEnable && selectionEnable;
        if (this.moveUpButton != null) {
            this.moveUpButton.setEnabled(moveState);
        }
        if (this.moveDownButton != null) {
            this.moveDownButton.setEnabled(moveState);
        }
        if (this.removeButton != null) {
            this.removeButton.setEnabled(removeEnable);
        }
    }

    private void addSorter(final TreeViewer viewer2) {
        final Tree table = viewer2.getTree();
        Listener sortListener = new Listener() {

            private int direction = 1;

            @Override
            public void handleEvent(Event e) {
                final TreeColumn column = (TreeColumn) e.widget;

                if (column == table.getSortColumn()) {
                    direction = -direction;
                }
                if (direction == 1) {
                    table.setSortDirection(SWT.UP);
                } else {
                    table.setSortDirection(SWT.DOWN);
                }

                table.setSortColumn(column);
                viewer2.setSorter(new ViewerSorter() {

                    int index = 0;

                    @Override
                    public void sort(Viewer viewer, Object[] elements) {
                        boolean found = false;
                        // find the sort column index
                        for (index = 0; index < table.getColumns().length; index++) {
                            if (table.getColumn(index) == table.getSortColumn()) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            index = 0; // first one as default
                        }
                        super.sort(viewer, elements);
                    }

                    @Override
                    public int compare(Viewer viewer, Object e1, Object e2) {
                        ITableLabelProvider labelProvider = (ITableLabelProvider) viewer2.getLabelProvider();
                        String columnText = labelProvider.getColumnText(e1, index) != null ? labelProvider.getColumnText(e1,
                                index) : ""; //$NON-NLS-1$
                        String columnText2 = labelProvider.getColumnText(e2, index) != null ? labelProvider.getColumnText(e2,
                                index) : ""; //$NON-NLS-1$
                        return getComparator().compare(columnText, columnText2) * direction;
                    }
                });
            }
        };
        table.getColumn(0).addListener(SWT.Selection, sortListener);
        if (getContexts().size() > 0) {
            for (int i = 0; i < getContexts().size(); i++) {
                table.getColumn(i + 1).addListener(SWT.Selection, sortListener);
            }
        }
        table.setSortColumn(table.getColumn(0));
        table.setSortDirection(SWT.UP);
    }

    private void activateCellEditor(final TreeItem item, final Tree tree, final TreeEditor treeEditor, int columnIndex, int column) {

        IContextParameter para = cellModifier.getRealParameter(properties[column], item.getData());

        if (para == null) {
            return;
        }
        valueChecker.checkErrors(item, column, para);
        if (!para.isBuiltIn()) {
            // not built-in
            return;
        }
        cellEditor = cellFactory.getCustomCellEditor(para, tree);

        if (cellEditor == null) {
            // unable to create the editor
            return;
        }

        // activate the cell editor
        cellEditor.activate();
        // if the cell editor has no control we can stop now
        Control control = cellEditor.getControl();
        if (control == null) {
            cellEditor.deactivate();
            cellEditor = null;
            return;
        }
        Text textControl = valueChecker.getTextControl(control);
        if (textControl != null) {
            if (ContextParameterUtils.isPasswordType(para)) {
                textControl.setEchoChar('*');
            } else {
                textControl.setEchoChar((char) 0);
            }
        }

        valueChecker.register(control);
        // add our editor listener
        cellEditor.addListener(createEditorListener(treeEditor, column));

        // set the layout of the tree editor to match the cell editor
        CellEditor.LayoutData layout = cellEditor.getLayoutData();
        treeEditor.horizontalAlignment = layout.horizontalAlignment;
        treeEditor.grabHorizontal = layout.grabHorizontal;
        treeEditor.minimumWidth = layout.minimumWidth;

        treeEditor.setEditor(control, item, column);
        // give focus to the cell editor
        cellEditor.setFocus();

    }

    protected void handleSelect(final TreeItem item, final Tree tree, final TreeEditor treeEditor, int columnIndex, int column) {
        // get the new selection
        activateCellEditor(item, tree, treeEditor, columnIndex, column);
    }

    @Override
    public boolean isGroupBySource() {
        boolean isRepositoryContext = false;
        if (modelManager != null) {
            isRepositoryContext = (modelManager instanceof ContextComposite)
                    && ((ContextComposite) modelManager).isRepositoryContext();
        }
        boolean value = getPreferenceStore().getBoolean(ITalendCorePrefConstants.CONTEXT_GROUP_BY_SOURCE);
        return value && !isRepositoryContext;
    }

    private void deactivateCellEditor(final TreeEditor tableEditor, int columnIndex) {
        tableEditor.setEditor(null, null, columnIndex);
        if (cellEditor != null) {
            Control control = cellEditor.getControl();
            if (control != null) {
                valueChecker.unregister(control);
            }
            cellEditor.deactivate();
            cellEditor.removeListener(editorListener);
            cellEditor = null;
        }
    }

    private ICellEditorListener createEditorListener(final TreeEditor tableEditor, final int columnIndex) {
        editorListener = new ICellEditorListener() {

            @Override
            public void cancelEditor() {
                deactivateCellEditor(tableEditor, columnIndex);
            }

            @Override
            public void editorValueChanged(boolean oldValidState, boolean newValidState) {
            }

            @Override
            public void applyEditorValue() {
                editing = true;
            }
        };
        return editorListener;
    }

    /**
     * bqian Comment method "createMenuBar".
     * 
     * @param toolBar
     */
    private void createToolBar(final ToolBar toolBar) {
        GridLayout layout = new GridLayout(2, true);
        GridData gridData = new GridData();
        toolBar.setLayout(layout);
        Label contextSeletLabel = new Label(toolBar, SWT.NULL);
        contextSeletLabel.setText("Default context enviroment");

        configContext = new ConfigureContextAction(modelManager, this.getShell());
        List<String> contextsDisplayList = new ArrayList();
        // List<IContext> list = new ArrayList<IContext>(getContextManager().getListContext());
        // for (IContext context : list) {
        // contextsDisplayList.add(context.getName());
        // }
        // contextsCombo = new Combo(toolBar);
        // // configure the visible item of database combo
        // int visibleItemCount = contextsCombo.getCombo().getItemCount();
        // if (visibleItemCount > 20) {
        // visibleItemCount = 20;
        // }
        // contextsCombo.getCombo().setVisibleItemCount(visibleItemCount);

        contextConfigButton = new Button(toolBar, SWT.PUSH);
        contextConfigButton.setImage(ImageProvider.getImage(configContext.getImageDescriptor()));
        contextConfigButton.setToolTipText(configContext.getText());
        contextConfigButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                configContext.run();
            }
        });
    }

    private void createContextsGroup(Composite parentComposite) {
        Composite contextsSelectComp = new Composite(parentComposite, SWT.NULL);
        contextsSelectComp.setLayout(new GridLayout(3, false));
        GridLayout layout2 = (GridLayout) contextsSelectComp.getLayout();
        layout2.marginHeight = 0;
        layout2.marginTop = 0;
        layout2.marginBottom = 0;

        Label contextSeletLabel = new Label(contextsSelectComp, SWT.NULL);
        contextSeletLabel.setText("Default context enviroment");
        contextsCombo = new Combo(contextsSelectComp, SWT.READ_ONLY);
        contextsCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                Object obj = e.getSource();
                String selectContext = ((Combo) obj).getText();
                IContext defaultContext = modelManager.getContextManager().getDefaultContext();
                if (selectContext.equals(defaultContext.getName())) {
                } else {
                    IContext newSelContext = null;
                    for (IContext enviroContext : modelManager.getContextManager().getListContext()) {
                        if (selectContext.equals(enviroContext.getName())) {
                            newSelContext = enviroContext;
                        }
                    }
                    modelManager.onContextChangeDefault(modelManager.getContextManager(), newSelContext);
                    refresh();
                    // fTableViewer.refresh(true);
                }
            }
        });

        configContext = new ConfigureContextAction(modelManager, this.getShell());
        contextConfigButton = new Button(contextsSelectComp, SWT.NULL);
        contextConfigButton.setImage(ImageProvider.getImage(configContext.getImageDescriptor()));
        contextConfigButton.setToolTipText(configContext.getText());
        contextConfigButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                configContext.run();
            }
        });
    }

    @Override
    public void setEnabled(boolean enabled) {
        configContext.setEnabled(enabled);

    }

    private ICellEditorListener editorListener;

    private CellEditor cellEditor;

    private String[] properties;

    private boolean editing;

    /**
     * bqian Comment method "getContexts".
     * 
     * @return
     */
    public List<IContext> getContexts() {
        List<IContext> contexts = new ArrayList<IContext>();
        IContextManager cm = modelManager.getContextManager();
        if (cm != null) {
            contexts = cm.getListContext();
        }
        return contexts;
    }

    @Override
    public IContextModelManager getContextModelManager() {
        return this.modelManager;
    }

    @Override
    public TreeViewer getViewer() {
        return this.viewer;
    }

    public ContextValueErrorChecker getValueChecker() {
        return this.valueChecker;
    }

    @Override
    public void refresh() {
        if (editing) {
            viewer.refresh();
            editing = false;
            return;
        }
        final Tree tree = viewer.getTree();
        TreeColumn[] columns = tree.getColumns();
        for (TreeColumn tableColumn : columns) {
            tableColumn.dispose();
        }
        List<IContext> contextList = getContexts();

        createColumnsAndCellEditors(tree, contextList);

        changeContextProvider();

        List<IContextParameter> contextTemplate = ContextTemplateComposite.computeContextTemplate(contextList);
        viewer.setInput(contextTemplate);
        viewer.expandAll();
        contextConfigButton.setEnabled(!modelManager.isReadOnly());
        for (IContext context : modelManager.getContextManager().getListContext()) {
            if (!Arrays.asList(contextsCombo.getItems()).contains(context.getName())) {
                contextsCombo.add(context.getName());
            }
        }

        for (int i = 0; i < contextsCombo.getItemCount(); i++) {
            IContext defaultContext = modelManager.getContextManager().getDefaultContext();
            if (defaultContext.getName().equals(contextsCombo.getItem(i))) {
                contextsCombo.select(i);
                break;
            }
        }
        int visibleItemCount = contextsCombo.getItemCount();
        if (visibleItemCount > 20) {
            visibleItemCount = 20;
        }
        contextsCombo.setVisibleItemCount(visibleItemCount);
        // (feature 1597)
        checkItemValueErrors(tree.getItems());
    }

    /**
     * cli Comment method "createColumnsAndCellEditors".
     */
    private void createColumnsAndCellEditors(final Tree tree, List<IContext> contextList) {
        TreeColumn column = new TreeColumn(tree, SWT.NONE);
        column.setText(Messages.getString("ConextTableValuesComposite.nameLabel")); //$NON-NLS-1$
        column.setWidth(ContextTableValuesComposite.CONTEXT_COLUMN_WIDTH);

        column = new TreeColumn(tree, SWT.NONE);
        column.setText(Messages.getString("ContextTemplateComposite.typeLabel")); //$NON-NLS-1$
        column.setWidth(ContextTableValuesComposite.CONTEXT_COLUMN_WIDTH);

        for (IContext context : contextList) {
            column = new TreeColumn(tree, SWT.NONE);
            column.setText(context.getName());
            column.setWidth(ContextTableValuesComposite.CONTEXT_COLUMN_WIDTH);
        }

        cellEditors = new CellEditor[getContexts().size() + 2];
        for (int i = 0; i < getContexts().size() + 2; i++) {
            if (i == 0) {
                cellEditors[i] = new TextCellEditor(tree);
            } else if (i == 1) {
                String[] values = ContextParameterJavaTypeManager.getJavaTypesLabels();
                cellEditors[i] = new ComboBoxCellEditor(tree, values, SWT.NULL);
            } else {
                cellEditors[i] = new TextCellEditor(tree);
            }
        }
        ((CCombo) cellEditors[1].getControl()).setEditable(false);

        properties = new String[contextList.size() + 2];
        properties[0] = ContextTableConstants.COLUMN_NAME_PROPERTY;
        properties[1] = ContextTableConstants.COLUMN_TYPE_PROPERTY;
        for (int i = 0; i < contextList.size(); i++) {
            properties[i + 2] = contextList.get(i).getName();
        }
        viewer.setColumnProperties(properties);
        viewer.setCellEditors(cellEditors);
    }

    public String[] getColumnProperties() {
        return this.properties;
    }

    private void checkItemValueErrors(final TreeItem[] items) {
        if (items == null) {
            return;
        }
        for (TreeItem item : items) {
            for (int i = 1; i < viewer.getColumnProperties().length; i++) {
                IContextParameter para = cellModifier.getRealParameter((String) viewer.getColumnProperties()[i], item.getData());
                if (para != null && para instanceof IContextParameter) {
                    valueChecker.checkErrors(item, i, para);
                }
            }
            checkItemValueErrors(item.getItems());
        }
    }

    /**
     * Clear the data in this viewer.
     * 
     * @param jobContextManager2
     */
    public void clear() {
        final Tree tree = viewer.getTree();
        TreeColumn[] columns = tree.getColumns();
        for (TreeColumn tableColumn : columns) {
            tableColumn.dispose();
        }
        viewer.setInput(Collections.EMPTY_LIST);
    }

    /**
     * DOC zli ContextCompare class global comment. Detailled comment
     */
    private class ContextCompare implements java.util.Comparator<IContext> {

        /*
         * (non-Javadoc)
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(IContext o1, IContext o2) {
            String name1 = o1.getName().toUpperCase();
            String name2 = o2.getName().toUpperCase();
            return name1.compareTo(name2);
        }
    }

}

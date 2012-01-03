/*
 * RedesNeuronalesView.java
 */

package redesneuronales;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.text.DecimalFormat;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;





/**
 * La ventana principal de la aplicación. Elementos de la interfaz gráfica.
 */
public class RedesNeuronalesView  extends FrameView {

/****************************************************************************
 * VARIABLES DE INICIO
 *****************************************************************************/

int iX=0, iY=0;//Variables para la captura del dibujo
int contPatrones=0;//Variable para indicar el No de patrones ingresados
//Matriz para capturar el patrón en tiempo real
double matriz[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
int ID=0;

double matPatrones[][]=new double[10][35];//Array temporal con los patrones
String nomPatrones[]= new String[10];//Array String con los nombres de los patrones
double matObjetivos[][]=new double[10][10];//Array con los valores objetivo

Red miRedJA=null;

Matriz entrada;


//MATRICES (OBJETOS)
Matriz patronesM;
Matriz objetivosM;
 



/**
 * Crear el fame principal
 * @param frame
 */
@Override
    public void setFrame(JFrame frame) {
        super.setFrame(frame);
      this.getFrame().setResizable(false);
      
    }



/**
 *
 * @param app
 */
public RedesNeuronalesView(SingleFrameApplication app) {
        super(app);
/****************************************************************************
 * RUTINAS DE INICIALIZACIÓN
 *****************************************************************************/

        //INICIAR VECTOR OBJETIVO
int pos=0;//variable para inicializar la matriz objetivo
        for (int i=0;i<10;i++){
                for (int j=0;j<10;j++){
                if(j==pos){
                    matObjetivos[i][j]=1;}
                else{
                 matObjetivos[i][j]=0;
                }
               
                }
                pos++;
        }


        //INICIO DEL FRAME Y MENSAJES DE BIENVENIDA
        initComponents();
        this.getFrame().setResizable(false);
        label5.setForeground(Color.red);
        label5.setText("APRENDIZAJE");
        label2.setText("Vector de entrada:[0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0]");
        label7.setText("1");
        debug.setText("Iniciando la aplicación...\nEn ésta ventana apareceran los mensajes del sistema\n");
        debug.append("\nMODO DE APRENDIZAJE\n");
        debug.append("La red neuronal requiere 10 patrones de dibujo a mano alzada con sus respectivos nombres para configurarse.\n");
        debug.append("Por favor, dibuje la forma del patrón en el recuadro de dibujo.\n");
        debug.append("Luego introduzca un nombre para el patrón y de clic al botón \"Agregar patrón\"\n\n");
        entrenar.setEnabled(false);
        calcularJ.setEnabled(false);
       

       //OCULTACIÓN DE ELEMENTOS GRÁFICOS
      
    
     
        
        

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

/**
 *
 */
@Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = RedesNeuronalesApp.getApplication().getMainFrame();
            aboutBox = new RedesNeuronalesAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        RedesNeuronalesApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        zona = new java.awt.Canvas();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        label5 = new java.awt.Label();
        textField1 = new java.awt.TextField();
        label6 = new java.awt.Label();
        label7 = new java.awt.Label();
        debug = new java.awt.TextArea();
        label8 = new java.awt.Label();
        label9 = new java.awt.Label();
        respuesta = new java.awt.Label();
        jLabel2 = new javax.swing.JLabel();
        label11 = new java.awt.Label();
        label1 = new java.awt.Label();
        button1 = new javax.swing.JButton();
        entrenar = new javax.swing.JButton();
        calcularJ = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setMaximumSize(new java.awt.Dimension(440, 280));
        mainPanel.setMinimumSize(new java.awt.Dimension(440, 280));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        mainPanel.setRequestFocusEnabled(false);
        mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mainPanelMouseEntered(evt);
            }
        });
        mainPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                mainPanelMouseMoved(evt);
            }
        });

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(redesneuronales.RedesNeuronalesApp.class).getContext().getResourceMap(RedesNeuronalesView.class);
        zona.setBackground(resourceMap.getColor("zona.background")); // NOI18N
        zona.setName("zona"); // NOI18N
        zona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                zonaMousePressed(evt);
            }
        });
        zona.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                zonaComponentShown(evt);
            }
        });
        zona.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                zonaMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                zonaMouseMoved(evt);
            }
        });
        zona.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                zonaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                zonaFocusLost(evt);
            }
        });
        zona.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                zonaPropertyChange(evt);
            }
        });

        label2.setAlignment(java.awt.Label.CENTER);
        label2.setName("label2"); // NOI18N
        label2.setText(resourceMap.getString("label2.text")); // NOI18N

        label3.setFont(resourceMap.getFont("label3.font")); // NOI18N
        label3.setForeground(resourceMap.getColor("label3.foreground")); // NOI18N
        label3.setName("label3"); // NOI18N
        label3.setText(resourceMap.getString("label3.text")); // NOI18N

        label4.setFont(resourceMap.getFont("label4.font")); // NOI18N
        label4.setName("label4"); // NOI18N
        label4.setText(resourceMap.getString("label4.text")); // NOI18N

        label5.setAlignment(java.awt.Label.CENTER);
        label5.setFont(resourceMap.getFont("label5.font")); // NOI18N
        label5.setName("label5"); // NOI18N
        label5.setText(resourceMap.getString("label5.text")); // NOI18N

        textField1.setName("textField1"); // NOI18N
        textField1.setText(resourceMap.getString("textField1.text")); // NOI18N

        label6.setName("labelPatron"); // NOI18N
        label6.setText(resourceMap.getString("labelPatron.text")); // NOI18N

        label7.setName("label7"); // NOI18N
        label7.setText(resourceMap.getString("label7.text")); // NOI18N

        debug.setBackground(resourceMap.getColor("debug.background")); // NOI18N
        debug.setEditable(false);
        debug.setFont(resourceMap.getFont("debug.font")); // NOI18N
        debug.setForeground(resourceMap.getColor("debug.foreground")); // NOI18N
        debug.setName("debug"); // NOI18N

        label8.setFont(resourceMap.getFont("label8.font")); // NOI18N
        label8.setName("label8"); // NOI18N
        label8.setText(resourceMap.getString("label8.text")); // NOI18N

        label9.setName("label9"); // NOI18N

        respuesta.setFont(resourceMap.getFont("respuesta.font")); // NOI18N
        respuesta.setForeground(resourceMap.getColor("respuesta.foreground")); // NOI18N
        respuesta.setName("respuesta"); // NOI18N
        respuesta.setText(resourceMap.getString("respuesta.text")); // NOI18N

        jLabel2.setIcon(resourceMap.getIcon("jLabel2.icon")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        label11.setFont(resourceMap.getFont("label11.font")); // NOI18N
        label11.setForeground(resourceMap.getColor("label11.foreground")); // NOI18N
        label11.setName("label11"); // NOI18N
        label11.setText(resourceMap.getString("label11.text")); // NOI18N

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(resourceMap.getFont("label1.font")); // NOI18N
        label1.setName("label1"); // NOI18N
        label1.setText(resourceMap.getString("label1.text")); // NOI18N

        button1.setText(resourceMap.getString("button1.text")); // NOI18N
        button1.setName("button1"); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        entrenar.setText(resourceMap.getString("entrenar.text")); // NOI18N
        entrenar.setName("entrenar"); // NOI18N
        entrenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entrenarActionPerformed(evt);
            }
        });

        calcularJ.setText(resourceMap.getString("calcularJ.text")); // NOI18N
        calcularJ.setName("calcularJ"); // NOI18N
        calcularJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularJActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(232, Short.MAX_VALUE)
                .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(224, 224, 224))
            .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(calcularJ, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(label11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(entrenar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                        .addGap(44, 44, 44)
                        .addComponent(jLabel2)
                        .addGap(174, 174, 174)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(zona, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addGap(50, 50, 50)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textField1, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                            .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(respuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(debug, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(23, 23, 23)
                                .addComponent(button1))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(entrenar)
                                        .addGap(28, 28, 28)
                                        .addComponent(calcularJ))
                                    .addComponent(zona, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(respuesta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addComponent(debug, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N
        fileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuActionPerformed(evt);
            }
        });

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(redesneuronales.RedesNeuronalesApp.class).getContext().getActionMap(RedesNeuronalesView.class, this);
        jMenuItem1.setAction(actionMap.get("reinciar")); // NOI18N
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        fileMenu.add(jMenuItem1);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addGap(508, 508, 508)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(statusAnimationLabel)
                        .addContainerGap())))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void zonaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zonaMousePressed
 /****************************************************************************
 * AREA DE DIBUJO: MOUSE PRESIONADO
 *****************************************************************************/
        //Capturo la posción del evento
         int fX = evt.getX();
        int fY = evt.getY();
        Graphics g=zona.getGraphics();


        iX=fX;
        iY=fY;
        int i;

     /*Código para llenar el vector de entrada
     Según la posición del puntero se determina la zona y se pone 1 o 0 en el
     vector correspondiente*/
        //Fila1
        if ((fY >0 && fY <=20)){
            if(fX >0 && fX <=20){matriz[0]=1;g.fillRect(0, 0, 20, 20);}
            if(fX >20 && fX <=40){matriz[1]=1;g.fillRect(20, 0, 20, 20);}
            if(fX >40 && fX <=60){matriz[2]=1;g.fillRect(40, 0, 20, 20);}
            if(fX >60 && fX <=80){matriz[3]=1;g.fillRect(60, 0, 20, 20);}
            if(fX >80 && fX <=100){matriz[4]=1;g.fillRect(80, 0, 20, 20);}
        }
         //Fila2
        if ((fY >20 && fY <=40)){
            if(fX >0 && fX <=20){matriz[5]=1;g.fillRect(0, 20, 20, 20);}
            if(fX >20 && fX <=40){matriz[6]=1;g.fillRect(20, 20, 20, 20);}
            if(fX >40 && fX <=60){matriz[7]=1;g.fillRect(40, 20, 20, 20);}
            if(fX >60 && fX <=80){matriz[8]=1;g.fillRect(60, 20, 20, 20);}
            if(fX >80 && fX <=100){matriz[9]=1;g.fillRect(80, 20, 20, 20);}
        }
         //Fila3
        if ((fY >40 && fY <=60)){
            if(fX >0 && fX <=20){matriz[10]=1;g.fillRect(0, 40, 20, 20);}
            if(fX >20 && fX <=40){matriz[11]=1;g.fillRect(20, 40, 20, 20);}
            if(fX >40 && fX <=60){matriz[12]=1;g.fillRect(40, 40, 20, 20);}
            if(fX >60 && fX <=80){matriz[13]=1;g.fillRect(60, 40, 20, 20);}
            if(fX >80 && fX <=100){matriz[14]=1;g.fillRect(80,40, 20, 20);}
        }
         //Fila4
        if ((fY >60 && fY <=80)){
            if(fX >0 && fX <=20){matriz[15]=1;g.fillRect(0, 60, 20, 20);}
            if(fX >20 && fX <=40){matriz[16]=1;g.fillRect(20, 60, 20, 20);}
            if(fX >40 && fX <=60){matriz[17]=1;g.fillRect(40, 60, 20, 20);}
            if(fX >60 && fX <=80){matriz[18]=1;g.fillRect(60, 60, 20, 20);}
            if(fX >80 && fX <=100){matriz[19]=1;g.fillRect(80, 60, 20, 20);}
        }
         //Fila5
        if ((fY >80 && fY <=100)){
            if(fX >0 && fX <=20){matriz[20]=1;g.fillRect(0, 80, 20, 20);}
            if(fX >20 && fX <=40){matriz[21]=1;g.fillRect(20, 80, 20, 20);}
            if(fX >40 && fX <=60){matriz[22]=1;g.fillRect(40, 80, 20, 20);}
            if(fX >60 && fX <=80){matriz[23]=1;g.fillRect(60, 80, 20, 20);}
            if(fX >80 && fX <=100){matriz[24]=1;g.fillRect(80, 80, 20, 20);}
        }
         //Fila6
        if ((fY >100 && fY <=120)){
            if(fX >0 && fX <=20){matriz[25]=1;g.fillRect(0, 100, 20, 20);}
            if(fX >20 && fX <=40){matriz[26]=1;g.fillRect(20, 100, 20, 20);}
            if(fX >40 && fX <=60){matriz[27]=1;g.fillRect(40, 100, 20, 20);}
            if(fX >60 && fX <=80){matriz[28]=1;g.fillRect(60, 100, 20, 20);}
            if(fX >80 && fX <=100){matriz[29]=1;g.fillRect(80, 100, 20, 20);}
        }
         //Fila7
        if ((fY >120 && fY <=140)){
            if(fX >0 && fX <=20){matriz[30]=1;g.fillRect(0, 120, 20, 20);}
            if(fX >20 && fX <=40){matriz[31]=1;g.fillRect(20, 120, 20, 20);}
            if(fX >40 && fX <=60){matriz[32]=1;g.fillRect(40, 120, 20, 20);}
            if(fX >60 && fX <=80){matriz[33]=1;g.fillRect(60, 120, 20, 20);}
            if(fX >80 && fX <=100){matriz[34]=1;g.fillRect(80, 120, 20, 20);}
        }


        //Código para mostrar en tiempo real el valor del vector de entrada
        StringBuffer mat= new StringBuffer();
        mat.append(matriz[0]);
        for (i=1;i<matriz.length;i++){
        mat.append(" ");
        mat.append(matriz[i]);
        }
        String matS = mat.toString();

        label2.setText("Vector de entrada:["+ matS +"]");
        g.dispose();
    }//GEN-LAST:event_zonaMousePressed

    private void zonaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zonaMouseDragged
 /****************************************************************************
 * AREA DE DIBUJO: MOUSE ARRASTRADO
 *****************************************************************************/
       
        //Código para ir dibujando líneas mientras se arrastra el puntero
        int fX = evt.getX();
        int fY = evt.getY();
        Graphics g=zona.getGraphics();
        
        
        iX=fX;
        iY=fY;
        int i;

     /*Código para llenar el vector de entrada
     Según la posición del puntero se determina la zona y se pone 1 o 0 en el
     vector correspondiente*/
        //Fila1
        if ((fY >0 && fY <=20)){
            if(fX >0 && fX <=20){matriz[0]=1;g.fillRect(0, 0, 20, 20);}
            if(fX >20 && fX <=40){matriz[1]=1;g.fillRect(20, 0, 20, 20);}
            if(fX >40 && fX <=60){matriz[2]=1;g.fillRect(40, 0, 20, 20);}
            if(fX >60 && fX <=80){matriz[3]=1;g.fillRect(60, 0, 20, 20);}
            if(fX >80 && fX <=100){matriz[4]=1;g.fillRect(80, 0, 20, 20);}
        }
         //Fila2
        if ((fY >20 && fY <=40)){
            if(fX >0 && fX <=20){matriz[5]=1;g.fillRect(0, 20, 20, 20);}
            if(fX >20 && fX <=40){matriz[6]=1;g.fillRect(20, 20, 20, 20);}
            if(fX >40 && fX <=60){matriz[7]=1;g.fillRect(40, 20, 20, 20);}
            if(fX >60 && fX <=80){matriz[8]=1;g.fillRect(60, 20, 20, 20);}
            if(fX >80 && fX <=100){matriz[9]=1;g.fillRect(80, 20, 20, 20);}
        }
         //Fila3
        if ((fY >40 && fY <=60)){
            if(fX >0 && fX <=20){matriz[10]=1;g.fillRect(0, 40, 20, 20);}
            if(fX >20 && fX <=40){matriz[11]=1;g.fillRect(20, 40, 20, 20);}
            if(fX >40 && fX <=60){matriz[12]=1;g.fillRect(40, 40, 20, 20);}
            if(fX >60 && fX <=80){matriz[13]=1;g.fillRect(60, 40, 20, 20);}
            if(fX >80 && fX <=100){matriz[14]=1;g.fillRect(80,40, 20, 20);}
        }
         //Fila4
        if ((fY >60 && fY <=80)){
            if(fX >0 && fX <=20){matriz[15]=1;g.fillRect(0, 60, 20, 20);}
            if(fX >20 && fX <=40){matriz[16]=1;g.fillRect(20, 60, 20, 20);}
            if(fX >40 && fX <=60){matriz[17]=1;g.fillRect(40, 60, 20, 20);}
            if(fX >60 && fX <=80){matriz[18]=1;g.fillRect(60, 60, 20, 20);}
            if(fX >80 && fX <=100){matriz[19]=1;g.fillRect(80, 60, 20, 20);}
        }
         //Fila5
        if ((fY >80 && fY <=100)){
            if(fX >0 && fX <=20){matriz[20]=1;g.fillRect(0, 80, 20, 20);}
            if(fX >20 && fX <=40){matriz[21]=1;g.fillRect(20, 80, 20, 20);}
            if(fX >40 && fX <=60){matriz[22]=1;g.fillRect(40, 80, 20, 20);}
            if(fX >60 && fX <=80){matriz[23]=1;g.fillRect(60, 80, 20, 20);}
            if(fX >80 && fX <=100){matriz[24]=1;g.fillRect(80, 80, 20, 20);}
        }
         //Fila6
        if ((fY >100 && fY <=120)){
            if(fX >0 && fX <=20){matriz[25]=1;g.fillRect(0, 100, 20, 20);}
            if(fX >20 && fX <=40){matriz[26]=1;g.fillRect(20, 100, 20, 20);}
            if(fX >40 && fX <=60){matriz[27]=1;g.fillRect(40, 100, 20, 20);}
            if(fX >60 && fX <=80){matriz[28]=1;g.fillRect(60, 100, 20, 20);}
            if(fX >80 && fX <=100){matriz[29]=1;g.fillRect(80, 100, 20, 20);}
        }
         //Fila7
        if ((fY >120 && fY <=140)){
            if(fX >0 && fX <=20){matriz[30]=1;g.fillRect(0, 120, 20, 20);}
            if(fX >20 && fX <=40){matriz[31]=1;g.fillRect(20, 120, 20, 20);}
            if(fX >40 && fX <=60){matriz[32]=1;g.fillRect(40, 120, 20, 20);}
            if(fX >60 && fX <=80){matriz[33]=1;g.fillRect(60, 120, 20, 20);}
            if(fX >80 && fX <=100){matriz[34]=1;g.fillRect(80, 120, 20, 20);}
        }


        //Código para mostrar en tiempo real el valor del vector de entrada
        StringBuffer mat= new StringBuffer();
        mat.append(matriz[0]);
        for (i=1;i<matriz.length;i++){
        mat.append(" ");
        mat.append(matriz[i]);
        }
        String matS = mat.toString();
      
        label2.setText("Vector de entrada:["+ matS +"]");
        g.dispose();

    }//GEN-LAST:event_zonaMouseDragged

    private void zonaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zonaMouseMoved
         this.dibujarGuias();
    }//GEN-LAST:event_zonaMouseMoved

    private void zonaComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_zonaComponentShown
        // TODO add your handling code here:
       this.dibujarGuias();
    }//GEN-LAST:event_zonaComponentShown

    private void zonaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_zonaPropertyChange
        // TODO add your handling code here:
  
      
    }//GEN-LAST:event_zonaPropertyChange

    private void zonaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_zonaFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_zonaFocusGained

    private void mainPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseEntered
        // TODO add your handling code here:
        //this.dibujarGuias();
    }//GEN-LAST:event_mainPanelMouseEntered

    private void mainPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseMoved
   
    }//GEN-LAST:event_mainPanelMouseMoved

    private void zonaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_zonaFocusLost
        // TODO add your handling code here:
   
    }//GEN-LAST:event_zonaFocusLost

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        /****************************************************************************
 * BOTÓN: AGREGAR PATRÓN
 *****************************************************************************/

  zona.repaint();
        dibujarGuias();
        
    /*se captura el array de patrones temporal(1x35) y se guarda en el array
     * final de patrones en la fila=contPatrones*/
    for (int j=0;j<35;j++)matPatrones[contPatrones][j]=matriz[j];
    nomPatrones[contPatrones]=textField1.getText();//Se guarda el nombre del patrón

    //Se pasa el valor del patrón capturado a String para visualizarlo en debug
        StringBuffer mat= new StringBuffer();
        mat.append(matriz[0]);
        for (int i=1;i<35;i++){
        mat.append(" ");
        mat.append(matriz[i]);
        }
        String matS = mat.toString();

        debug.append("¡Capturado patrón "+String.valueOf(contPatrones+1)+" con éxito!\n");
        debug.append("Nombre: \""+nomPatrones[contPatrones]+"\" valor:\n");
        debug.append("["+ matS +"]\n");

        //se incremente al contador de patrones y se actualizan las etiquetas
        contPatrones++;
        label7.setText(String.valueOf(contPatrones+1));
        textField1.setText("");


        //SI SE HA ALCANZADO EL NÚMERO MÁXIMO DE PATRONES:

        if (contPatrones==10){
                //Deshabilito elementos gráficos y cambio el estado
                label7.setVisible(false);
                button1.setEnabled(false);
                label6.setEnabled(false);
                textField1.setEnabled(false);
               


                label5.setText("EJECUCIÓN");
                label5.setForeground(Color.GREEN);

                patronesM = new Matriz(matPatrones);//Se inicializa la matriz de patrones
                patronesM = Matriz.transponer(patronesM);//Se transpone => 35x10
                objetivosM= new Matriz(matObjetivos);//Se inicializa la matriz objetivos
                objetivosM= Matriz.transponer(objetivosM);//Se transpone => 10x10

                miRedJA = new Red(patronesM,objetivosM);
                

                debug.append("\nMatriz de objetivos ["+objetivosM.getFilString()+"x"+objetivosM.getColString()+"]:\n");
                debug.append(objetivosM.toStringM());


                debug.append("\nMatriz de patrones ["+patronesM.getFilString()+"x"+patronesM.getColString()+"]:\n");
                debug.append(patronesM.toStringM());

                //guardar las matrices para matlab
             
                debug.append("\nMODO DE EJECUCIÓN\n");
                debug.append("Para comenzar a utilizar la red neuronal calculada en JAVA presione primero \"Entrenar\" y luego\n ");
                debug.append("\"CalcularJ\" bajo el título Java de ésta interfaz \n");
                //nuevos elementos gráficos
                entrenar.setEnabled(true);
                
           

        }



       //Reiniciar vector de entrada
      for(int j=0;j<=34;j++){
      matriz[j]=0;

      }

          zona.repaint();
        dibujarGuias();
        StringBuffer mat1= new StringBuffer();
        mat1.append(matriz[0]);
        for (int i=1;i<matriz.length;i++){
        mat1.append(" ");
        mat1.append(matriz[i]);
        }

          zona.repaint();
        dibujarGuias();
        String matS1 = mat1.toString();
        label2.setText("Vector de entrada:["+ matS1 +"]");
        
      
       
    }//GEN-LAST:event_button1ActionPerformed

    private void entrenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entrenarActionPerformed
       
        // TODO add your handling code here:
       calcularJ.setEnabled(false);
        
       
       String alfaS = JOptionPane.showInputDialog(null, "Por favor digite el factor de aprendizaje (alfa)");
       double alfa= Double.valueOf(alfaS);
       
       String errorS = JOptionPane.showInputDialog(null, "Por favor digite el valor de error mínimo por patrón (ep)");
       double error= Double.valueOf(errorS);  
       
       String itS = JOptionPane.showInputDialog(null, "Por favor digite el número máximo de iteraciones (épocas)");
       int it= Integer.valueOf(itS);
       debug.append("\nEntrenando la red por favor espere...");
            
          String res=Red.trainNetLog(miRedJA, alfa,error, it);

          debug.append(res);
          calcularJ.setEnabled(true);
          zona.repaint();
        dibujarGuias();
      
    }//GEN-LAST:event_entrenarActionPerformed

    private void calcularJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularJActionPerformed
        // TODO add your handling code here:

        Matriz tmp = new Matriz(matriz);
        tmp=Matriz.transponer(tmp);

        Matriz resp= Red.simNet(miRedJA, tmp);
        DecimalFormat df = new DecimalFormat("0.00");

        debug.append("\n\nRESULTADOS SEGÚN LA RED NEURONAL DE JAVA:\n");
        for (int i=0;i<10;i++){
        debug.append(nomPatrones[i]+": "+String.valueOf(df.format(Matriz.transponer(resp).toArray()[0][i]))+"  ");
        }
        debug.append("\n");

        //Inicializo de nuevo la matriz de captura y redibujo el lienzo
        for(int j=0;j<=34;j++){
              matriz[j]=0;
              }
      
        tmp=new Matriz(Matriz.transponer(resp).toArray()[0]);
        respuesta.setText(Matriz.masProbable(tmp, nomPatrones));

       zona.repaint();
        dibujarGuias();
       
    }//GEN-LAST:event_calcularJActionPerformed

    private void fileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuActionPerformed


    }//GEN-LAST:event_fileMenuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button1;
    private javax.swing.JButton calcularJ;
    private java.awt.TextArea debug;
    private javax.swing.JButton entrenar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private java.awt.Label label1;
    private java.awt.Label label11;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private java.awt.Label respuesta;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private java.awt.TextField textField1;
    private java.awt.Canvas zona;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;

private void dibujarGuias(){
 Graphics g=zona.getGraphics();
        g.setColor(Color.blue);
        g.drawLine(0, 0, 0, 140);
        g.drawLine(20, 0, 20, 140);
        g.drawLine(40, 0, 40, 140);
        g.drawLine(60, 0, 60, 140);
        g.drawLine(80, 0, 80, 140);
        g.drawLine(99, 0, 99, 140);

        g.drawLine(0, 0, 100, 0);
        g.drawLine(0, 20, 100, 20);
        g.drawLine(0, 40, 100, 40);
        g.drawLine(0, 60, 100, 60);
        g.drawLine(0, 80, 100, 80);
        g.drawLine(0, 100, 100, 100);
        g.drawLine(0, 120, 100, 120);
        g.drawLine(0, 139, 100, 139);
        g.dispose();
}

/**
 * Reiniciar aplicación
 * Reinicia los vectores y en general todos los datos de la aplicación
 */
@Action
    public void reinciar() {
             // TODO add your handling code here:
         contPatrones=0;
                label7.setVisible(true);
                button1.setEnabled(true);
                label6.setEnabled(true);
                textField1.setEnabled(true);
                label5.setForeground(Color.red);
        label5.setText("APRENDIZAJE");
        label2.setText("Vector de entrada:[0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0]");
        label7.setText("1");
        debug.setText("Iniciando la aplicación...\nEn ésta ventana apareceran los mensajes del sistema\n");
        debug.append("\nMODO DE APRENDIZAJE\n");
        debug.append("La red neuronal requiere 10 patrones de dibujo a mano alzada con sus respectivos nombres para configurarse.\n");
        debug.append("Por favor, dibuje la forma del patrón en el recuadro de dibujo.\n");
        debug.append("Luego introduzca un nombre para el patrón y de clic al botón \"Agregar patrón\"\n\n");
        entrenar.setEnabled(false);
        calcularJ.setEnabled(false);
        zona.repaint();
        dibujarGuias();
        
    }



}

/*
 * Copyright (C) 2019 Randall Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alexandriasoftware.swing;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.text.PlainDocument;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Randall Wood
 */
class JInputValidatorSwingTest extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private final VerifyingValidator validator;

    /**
     * Creates new form JInputValidatorDemo
     */
    public JInputValidatorSwingTest() {
        initComponents();
        InputVerifier verifier = new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                int length = jTextField1.getText().length();
                return (length <= 0 || length >= 8); // true if empty or 8 or more characters
            }
        };
        validator = new VerifyingValidator(jTextField1,
                verifier,
                new Validation(Validation.Type.DANGER, "This is a DANGER state."),
                new Validation(Validation.Type.SUCCESS, "This is a SUCCESS state."),
                true,
                true,
                JInputValidatorPreferences.getPreferences());
        jTextField1.setInputVerifier(validator);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setText("jTextField1");
        jTextField1.setToolTipText("This is a valid state.");
        jTextField1.setName("jTextField1"); // NOI18N

        jLabel1.setText("VerifyingValidator demonstration.");

        jLabel2.setText("Shows danger if text length > 0 && < 8.");

        jLabel3.setText("Shows success after first input otherwise.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel3)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private JInputValidatorSwingTest frame;
    private FrameFixture window;

    @BeforeEach
    void setup() {
        frame = GuiActionRunner.execute(() -> new JInputValidatorSwingTest());
        window = new FrameFixture(frame);
    }

    @AfterEach
    void tearDown() {
        window.cleanUp();
    }

    @Test
    void testVerifyingValidator() {
        jTextField1.setText("");
        // manually call to assert return value
        assertTrue(validator.verify(jTextField1));
        assertEquals(Validation.Type.SUCCESS, validator.getValidation().getType());
        jTextField1.setText("123456");
        assertFalse(validator.verify(jTextField1));
        assertEquals(Validation.Type.DANGER, validator.getValidation().getType());
        jTextField1.setText("12345678");
        assertTrue(validator.verify(jTextField1));
        assertEquals(Validation.Type.SUCCESS, validator.getValidation().getType());
        jTextField1.setText("1234567890");
        assertTrue(validator.verify(jTextField1));
        assertEquals(Validation.Type.SUCCESS, validator.getValidation().getType());
    }

    @Test
    void testDocumentListener() {
        window.show();
        JTextComponentFixture field = window.textBox("jTextField1");
        assertEquals(Validation.Type.NONE, frame.validator.getValidation().getType());
        field.deleteText();
        assertEquals(Validation.Type.SUCCESS, frame.validator.getValidation().getType());
        field.enterText("1234");
        assertEquals(Validation.Type.DANGER, frame.validator.getValidation().getType());
        field.enterText("567890");
        assertEquals(Validation.Type.SUCCESS, frame.validator.getValidation().getType());
    }

    @Test
    void testChangingDocument() {
        window.show();
        JTextComponentFixture field = window.textBox("jTextField1");
        assertEquals(Validation.Type.NONE, frame.validator.getValidation().getType());
        field.deleteText();
        assertEquals(Validation.Type.SUCCESS, frame.validator.getValidation().getType());
        frame.jTextField1.setDocument(new PlainDocument());
        field.enterText("1234");
        assertEquals(Validation.Type.DANGER, frame.validator.getValidation().getType());
    }

    /**
     * Show the form until dismissed by user.
     * 
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JInputValidatorSwingTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(() -> {
            new JInputValidatorSwingTest().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

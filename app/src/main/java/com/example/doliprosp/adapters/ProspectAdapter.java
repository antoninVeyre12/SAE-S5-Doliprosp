package com.example.doliprosp.adapters;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doliprosp.R;
import com.example.doliprosp.modeles.Prospect;
import com.example.doliprosp.viewModels.MesProspectViewModel;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

// La classe ProspectAdapter est un adaptateur pour lier une liste de prospects à un RecyclerView.
// Elle implémente Serializable pour la sérialisation de l'adaptateur.
public class ProspectAdapter extends RecyclerView.Adapter<ProspectAdapter.MyViewHolder> implements Serializable {
    // Regex pour vérifier les champs lors de la modification
    private final String REGEX_MAIl = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String REGEX_TEL = "^(0[1-9])(\\s?\\d{2}){4}$";

    // Liste des prospects à afficher dans le RecyclerView
    private List<Prospect> prospectListe;
    private MesProspectViewModel mesProspectViewModel;

    // interfaces pour gérer les événements de clic sur les items
    private ProspectAdapter.OnItemClickListener onItemClickListener;

    // Constructeur de l'adaptateur qui prend la liste des prospects et un gestionnaire de clics
    public ProspectAdapter(List<Prospect> prospectListe, ProspectAdapter.OnItemClickListener onItemClickListener,
                           MesProspectViewModel mesProspectViewModel) {
        this.prospectListe = prospectListe;
        this.onItemClickListener = onItemClickListener;
        this.mesProspectViewModel = mesProspectViewModel;
    }

    // Cette méthode est appelée pour créer un nouvel item dans le RecyclerView.
    @NonNull
    @Override
    public ProspectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // On gonfle le layout item_prospect pour chaque élément de la liste
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prospect, parent, false);
        return new ProspectAdapter.MyViewHolder(view); // Retourne un ViewHolder contenant la vue gonflée
    }

    // Cette méthode est appelée pour lier les données du prospect aux vues correspondantes.
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Récupère le prospect à la position donnée dans la liste
        Prospect prospect = prospectListe.get(position);

        // Remplie le TextView avec le nom du prospect
        holder.nom.setText(prospect.getNom());

        // Définit un écouteur de clic sur l'icône
        holder.icone.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onSelectClick(position, prospectListe); // Déclenche le clic pour l'icône
            }
        });

        // Définit un écouteur de clic sur le nom du prospect
        holder.nom.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onSelectClick(position, prospectListe); // Déclenche le clic pour le nom du prospect
            }
        });

        // Clic sur le bouton modifier
        holder.prospect_modifier.setOnClickListener(v -> {
            afficherDialogModification(v, prospect, position);
            //    onItemClickListener.OnModifyClick(position, nouveauNomPrenom, nouveauMail, nouveauTel,
            //            nouvelleAdresse, nouvelleVille, nouveauCodePostal);
        });

        holder.prospect_supprimer.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                new AlertDialog.Builder(v.getContext())
                        .setMessage(R.string.confirmation_suppresion_prospect)
                        .setPositiveButton("Oui", (dialog, which) -> {
                            // Si l'utilisateur confirme, appeler la méthode de suppression
                            onItemClickListener.onDeleteClick(position);
                        })
                        .setNegativeButton("Non", (dialog, which) -> {
                            // L'utilisateur annule la suppression
                            dialog.dismiss();
                        })
                        .show();
            }
        });

        //String imageName = prospect.getImage();
        //int imageResId = holder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());
        // On affiche l'image
    }

    // Cette méthode retourne le nombre d'éléments à afficher dans le RecyclerView.
    @Override
    public int getItemCount() {
        return prospectListe.size();
    }

    // interfaces pour le gestionnaire de clics. Permet d'exécuter des actions lors du clic sur un item.
    public interface OnItemClickListener {
        void onSelectClick(int position, List<Prospect> prospectListe);

        void onDeleteClick(int position);

        void OnModifyClick(int position, String nouveauNomPrenom, String nouveauMail,
                           String nouveauTel, String nouvelleAdresse, String nouvelleVille,
                           String nouveauCodePostal);

    }

    // ViewHolder pour chaque item du RecyclerView.
    // Contient les vues d'un item : le nom du prospect et l'icône.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nom;
        public ImageView icone;
        public ImageButton prospect_supprimer;

        public ImageButton prospect_modifier;

        // Constructeur pour récupérer les vues par leur ID
        public MyViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nomprenom);
            icone = itemView.findViewById(R.id.icone);
            prospect_supprimer = itemView.findViewById(R.id.prospect_supprimer);
            prospect_modifier = itemView.findViewById(R.id.prospect_modifier);

        }
    }

    public void setProspectList(List<Prospect> nouvelleListe) {
        this.prospectListe = nouvelleListe;
        notifyDataSetChanged();
    }

    private void afficherDialogModification(View v, Prospect prospect, int
            position) {
        if (onItemClickListener != null) {
            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            View layout = inflater.inflate(R.layout.dialog_create_prospect, null);

            EditText editTextNomPrenom = layout.findViewById(R.id.editTextNomPrenom);
            EditText editTextMail = layout.findViewById(R.id.editTextMail);
            EditText editTextPhone = layout.findViewById(R.id.editTextPhone);
            EditText editTextAdresse = layout.findViewById(R.id.editTextAdresse);
            EditText editTextVille = layout.findViewById(R.id.editTextVille);
            EditText editTextCodePostal = layout.findViewById(R.id.editTextCodePostal);
            Button btnModifier = layout.findViewById(R.id.buttonSubmit);
            Button btnAnnuler = layout.findViewById(R.id.buttonCancel);
            TextView erreurProspect = layout.findViewById(R.id.erreur_prospect);

            LinearLayout premiereBarreRecherche =
                    layout.findViewById(R.id.premiereBarreRecherche);

            LinearLayout deuxiemeBarreRecherche =
                    layout.findViewById(R.id.deuxiemeBarreRecherche);

            AppCompatButton btnPlus = layout.findViewById(R.id.bouton_plus);
            AppCompatButton btnMoins = layout.findViewById(R.id.bouton_moins);

            premiereBarreRecherche.setVisibility(View.GONE);
            deuxiemeBarreRecherche.setVisibility(View.GONE);


            // Remplir les EditText avec les valeurs actuelles
            editTextNomPrenom.setText(prospect.getNom());
            editTextMail.setText(prospect.getMail());
            editTextPhone.setText(prospect.getNumeroTelephone());
            editTextAdresse.setText(prospect.getAdresse());
            editTextVille.setText(prospect.getVille());
            editTextCodePostal.setText(prospect.getCodePostal());

            btnModifier.setText("Modifier");
            // Créer une alerte pour confirmer la modification
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(v.getContext())
                    .setTitle("Modifier le prospect")
                    .setView(layout)
                    .setCancelable(false);

            AlertDialog dialog = builder.create();
            dialog.show();
            btnAnnuler.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            // Validation du nom lorsque l'utilisateur appuie sur "Confirmer"

            btnModifier.setOnClickListener(v1 -> {
                String nouveauNomPrenom = editTextNomPrenom.getText().toString();
                String nouveauMail = editTextMail.getText().toString();
                String nouveauTel = editTextPhone.getText().toString();
                String nouvelleAdresse = editTextAdresse.getText().toString();
                String nouvelleVille = editTextVille.getText().toString();
                String nouveauCodePostal = editTextCodePostal.getText().toString();

                // VERIFIER LES INFOS AU MOMENT DU RETOUR SUR L'APPLI

                // Vérification du nom
                if (nouveauNomPrenom.length() <= 2) {
                    erreurProspect.setText(R.string.erreur_nom_prospect_longueur);
                    erreurProspect.setVisibility(View.VISIBLE);
                }
                // Vérification du mail
                else if (!nouveauMail.matches(REGEX_MAIl)) {
                    erreurProspect.setText(R.string.erreur_mail_prospect);
                    erreurProspect.setVisibility(View.VISIBLE);
                }
                // Vérification du téléphone
                else if (!nouveauTel.startsWith("0")) {
                    erreurProspect.setText(R.string.erreur_tel_prospect_zero);
                    erreurProspect.setVisibility(View.VISIBLE);
                } else if (!nouveauTel.matches(REGEX_TEL)) {
                    erreurProspect.setText(R.string.erreur_tel_prospect);
                    erreurProspect.setVisibility(View.VISIBLE);
                }
                // Vérification de l'adresse
                else if (nouvelleAdresse.isEmpty()) {
                    erreurProspect.setText(R.string.erreur_adresse_prospect_vide);
                    erreurProspect.setVisibility(View.VISIBLE);
                }
                // Vérification de la ville
                else if (nouvelleVille.isEmpty()) {
                    erreurProspect.setText(R.string.erreur_ville_prospect_vide);
                    erreurProspect.setVisibility(View.VISIBLE);
                }
                // Vérification du code postal
                else if (nouveauCodePostal.length() < 5) {
                    erreurProspect.setText(R.string.erreur_codePostal_prospect);
                    erreurProspect.setVisibility(View.VISIBLE);
                } else {
                    erreurProspect.setTextColor(Color.RED);
                    erreurProspect.setVisibility(View.GONE);
                    dialog.dismiss();
                    onItemClickListener.OnModifyClick(position, nouveauNomPrenom, nouveauMail, nouveauTel, nouvelleAdresse, nouvelleVille, nouveauCodePostal);
                }
            });
        }
    }
}
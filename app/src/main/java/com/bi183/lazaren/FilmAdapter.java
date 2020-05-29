package com.bi183.lazaren;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private Context context;
    private ArrayList<Film> dataFilm;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");

    public FilmAdapter(Context context, ArrayList<Film> dataFilm) {
        this.context = context;
        this.dataFilm = dataFilm;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_film, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        Film tempFilm = dataFilm.get(position);
        holder.idFilm = tempFilm.getIdFilm();
        holder.tvJudul.setText(tempFilm.getJudul());
        holder.tvHeadline.setText(tempFilm.getSinopsis());
        holder.gambar = tempFilm.getGambar();
        holder.tanggal = sdFormat.format(tempFilm.getTanggal());
        holder.link = tempFilm.getLinkTrailer();

        try {
            File file = new File(holder.gambar);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.imgFilm.setImageBitmap(bitmap);
            holder.imgFilm.setContentDescription(holder.gambar);
        } catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(context, "Gagal mengambil gambar dari storage", Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    public int getItemCount() {
        return dataFilm.size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ImageView imgFilm;
        private TextView tvJudul, tvHeadline;
        private int idFilm;
        private String tanggal, gambar, link;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFilm = itemView.findViewById(R.id.iv_film);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvHeadline = itemView.findViewById(R.id.tv_headline);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent openFilm = new Intent(context, TampilActivity.class);
            openFilm.putExtra("ID", idFilm);
            openFilm.putExtra("JUDUL", tvJudul.getText().toString());
            openFilm.putExtra("GAMBAR", gambar);
            openFilm.putExtra("TANGGAL", tanggal);
            openFilm.putExtra("SINOPSIS", tvHeadline.getText().toString());
            openFilm.putExtra("LINK", link);

            context.startActivity(openFilm);
        }

        @Override
        public boolean onLongClick(View v) {

            Intent openInput = new Intent(context, InputActivity.class);
            openInput.putExtra("OPERASI", "update");
            openInput.putExtra("ID", idFilm);
            openInput.putExtra("JUDUL", tvJudul.getText().toString());
            openInput.putExtra("GAMBAR", gambar);
            openInput.putExtra("TANGGAL", tanggal);
            openInput.putExtra("SINOPSIS", tvHeadline.getText().toString());
            openInput.putExtra("LINK", link);

            context.startActivity(openInput);

            return true;
        }
    }
}

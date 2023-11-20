package hitbeat.dao;

import java.util.Date;
import java.util.List;

import hitbeat.model.Playlist;
import hitbeat.model.Track;

public class PlaylistDAO extends BaseDAO<Playlist>{
    public PlaylistDAO(){
        super(Playlist.class);
        
        if (this.getAll().isEmpty()) {
            this.bulkCreateOrUpdate(List.of(
                new Playlist("Musicas Curtidas", "Musicas que o usuario marcou como curtida",
                    null, new Date(), null)
            ), "name");
        }
    }
    
    @Override
    protected void updateProperties(Playlist existingEntity, Playlist newEntity) {
        existingEntity.setName(newEntity.getName());
        existingEntity.setDescription(newEntity.getDescription());
        existingEntity.setCover(newEntity.getCover());
    }

    public List<Track> getAllTracks(Playlist playlist) {
        return this.executeMethod(session -> {
            String hql = "SELECT pt.track FROM PlaylistTrack pt WHERE pt.playlist = :playlist";
            return session.createQuery(hql, Track.class)
                    .setParameter("playlist", playlist)
                    .list();
        });
    }
}

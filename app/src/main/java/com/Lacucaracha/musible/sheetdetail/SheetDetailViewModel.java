package com.lacucaracha.musible.sheetdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lacucaracha.musible.data.MusicSheet;
import com.lacucaracha.musible.data.source.SheetRepository;
import com.lacucaracha.musible.sheetlist.SheetFilterType;
import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.util.MidiEventListener;
import com.leff.midi.util.MidiProcessor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.sherlock.com.sun.media.sound.SF2Soundbank;
import cn.sherlock.com.sun.media.sound.SoftSynthesizer;
import jp.kshoji.javax.sound.midi.InvalidMidiDataException;
import jp.kshoji.javax.sound.midi.Receiver;
import jp.kshoji.javax.sound.midi.ShortMessage;

public class SheetDetailViewModel extends ViewModel {
    public static String URL = "file:///android_asset/index.html";
    private final SheetRepository mRepository;
    private LiveData<MusicSheet> mMusicSheet;
    private boolean mIsPlay = false;
    private SF2Soundbank mSf ;
    private MidiProcessor mMidiProcessor;
    // TODO: Implement the ViewModel

    public SheetDetailViewModel(SheetRepository repository, SF2Soundbank sf) {
        mRepository= repository;
        mSf = sf;
    }


    public void start(String MusicSheetId){
        if(MusicSheetId!=null){
            mMusicSheet = mRepository.getMusicSheetWithId(MusicSheetId);
        }
    }

//    public File getmMidi() {
//        return mMidi;
//    }

    public void setmMidiProcessor(File mMidi) throws Exception
    {
        SoftSynthesizer synth = new SoftSynthesizer();
        synth.open();
        synth.loadAllInstruments(mSf);
        synth.getChannels()[0].programChange(0);
        synth.getChannels()[1].programChange(1);
        Receiver recv = synth.getReceiver();
        int channel = 0;
        MidiFile midiFile = new MidiFile(mMidi);
        MidiProcessor processor = new MidiProcessor(midiFile);
        processor.registerEventListener(new MidiEventListener() {
            @Override
            public void onStart(boolean fromBeginning) {

            }

            @Override
            public void onEvent(MidiEvent event, long ms) {
                if (event.getClass() == NoteOn.class) {

                    NoteOn noteOn = ((NoteOn) event);

                    try {
                        ShortMessage msg = new ShortMessage();
                        msg.setMessage(ShortMessage.NOTE_ON, channel, noteOn.getNoteValue(), noteOn.getVelocity());
                        recv.send(msg, ms);
                    } catch (InvalidMidiDataException e) {
                        e.printStackTrace();
                    }

                } else if (event.getClass() == NoteOff.class) {

                    NoteOff noteOff = ((NoteOff) event);

                    try {
                        ShortMessage msg = new ShortMessage();
                        msg.setMessage(ShortMessage.NOTE_ON, channel, noteOff.getNoteValue(), noteOff.getVelocity());
                        recv.send(msg, ms);
                    } catch (InvalidMidiDataException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onStop(boolean finished) {

            }
        }, MidiEvent.class);
        mMidiProcessor = processor;
    }

    public LiveData<MusicSheet> getMusicSheet(){
        return mMusicSheet;
    }

    public boolean isPlaying() {
        return mIsPlay;
    }

    public void playMidi()
    {

        mMidiProcessor.start();
        mIsPlay = true;
    }

    public void stopMidi() {
        mMidiProcessor.stop();
        mIsPlay = false;
    }
    public void destroyMidiProcessor(){
        if(mMidiProcessor!=null){
            mMidiProcessor.reset();
            mMidiProcessor.unregisterAllEventListeners();
        }
    }
}
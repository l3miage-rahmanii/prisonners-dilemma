package fr.uga.l3miage.pc.strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;

import java.util.List;

public class RancunierStrategie extends Strategie {
    private boolean aTrahi = false;


    public CoupEnum prochainCoup(List<CoupEnum> historiqueAdversaire) {
       if (historiqueAdversaire.get(historiqueAdversaire.size() - 1).equals(CoupEnum.TRAHIR)) {
            aTrahi = true;
        }

       if(aTrahi) {
           return CoupEnum.TRAHIR;
       }
       else return CoupEnum.COOPERER;

    }

    public boolean getAtrahi()
          {
              return aTrahi;
    }


}



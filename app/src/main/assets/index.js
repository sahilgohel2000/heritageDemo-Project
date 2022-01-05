// lookup for selection objects
var selection = {};
var container = document.getElementById('popup');
var content = document.getElementById('popup-content');
var closer = document.getElementById('popup-closer');
var geo_url = 'http://183.87.214.73:8080/geoserver';
var endpointapiurl = 'http://183.87.214.73:8080/heritage/api/';
var vector_kiosk, vectorSource_kiosk, icontype = 'pin',
  basemapurl = 'http://183.87.214.73:8080/INDIA_EPSG_3857/{z}/{x}/{y}.png',
  vector_mybyk, vectorSource_mybyk;
var transit_vector, vectorSource_transit, mylocation, selected_publicamenity = [],
  selected_publictransit = [];
var styleCache = {},ispublicamenityselected=false;
/**
 * Create an overlay to anchor the popup to the map.
 */

var overlay = new ol.Overlay({
  element: container,
  autoPan: true,
  positioning: 'top-right',
  offset: [25, -55],
  autoPanAnimation: {
    duration: 250
  }
});

function allbusstyle(feature, resolution) {
  var fill = new ol.style.Fill({
      color: 'rgba(255,255,255,0.4)'
    }),
    cnt = feature.get('cycleCount').toString();
  var stroke = new ol.style.Stroke({
    color: '#3399CC',
    width: 10.25
  });
  let defaultStyles =
    new ol.style.Style({
      text: new ol.style.Text({
        //font: '12px helvetica,sans-serif',
        font: '15px',
        text: cnt,
        scale: 1.3,
        fill: new ol.style.Fill({
          color: '#000'
        }),
        stroke: new ol.style.Stroke({
          color: '#fff',
          width: 3
        })
      }),
      image: new ol.style.Icon({
        anchor: [0.5, 11],
        scale: 1.5,
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        src: cnt > 0 ? './mybyk1.svg' : './mybyk0.svg',
      }),
      fill: fill,
      stroke: stroke
    });
  return defaultStyles;
}
container.addEventListener('click', function (evt) {
  if ('ol-popup-closer' != evt.target.className) {
    let dataid = this.querySelector('span').getAttribute('data-id');
    let type = this.querySelector('span').getAttribute('type');

    let dataId = {
      nid: dataid,
      type: type
    };
    // For Android
    if (window.appInterface) {
      window.appInterface.getPinInfo(dataid, type);
    }

    // For iOS
    if (window.webkit && window.webkit.messageHandlers) {
      window.webkit.messageHandlers.getPinInfo.postMessage(dataId);
    }
  }

});



/**
 * Add a click handler to hide the popup.
 * @return {boolean} Don't follow the href.
 */
closer.onclick = function () {
  if (overlay.setPosition) {
    overlay.setPosition(undefined);
    closer.blur();
    select.getFeatures().clear();
  }
  return false;
};

var vtLayer = new ol.layer.VectorTile({
  declutter: true,
  source: new ol.source.VectorTile({
    maxZoom: 15,
    format: new ol.format.MVT({
      idProperty: 'iso_a3'
    }),
    url: 'https://ahocevar.com/geoserver/gwc/service/tms/1.0.0/' +
      'ne:ne_10m_admin_0_countries@EPSG%3A900913@pbf/{z}/{x}/{-y}.pbf'
  }),
  style: function (feature) {
    var selected = !!selection[feature.getId()];
    return new ol.style.Style({
      stroke: new ol.style.Stroke({
        color: selected ? 'rgba(200,20,20,0.8)' : 'gray',
        width: selected ? 2 : 1
      }),
      fill: new ol.style.Fill({
        color: selected ? 'rgba(200,20,20,0.2)' : 'rgba(20,20,20,0.9)'
      })
    });
  }
});

var map = new ol.Map({
  overlays: [overlay],
  controls: ol.control.defaults({
    attribution: false,
    zoom: false,
  }),
  interactions: new ol.interaction.defaults({
    pinchRotate: false,
    rotate: false
  }).extend([new ol.interaction.MouseWheelZoom({
    constrainResolution: true // force zooming to a integer zoom
  }), new ol.interaction.PinchZoom(), new ol.interaction.DragPan({
    kinetic: null
  })]),
  layers: [
    /*new ol.layer.Tile({
      source: new ol.source.OSM({url:'https://a.tile.thunderforest.com/transport/${z}/${x}/${y}.png?apikey=6170aad10dfd42a38d4d8c709a536f38'})
    }),*/
    /*new ol.layer.Tile({
         source: new ol.source.Stamen({
           layer: 'terrain',
         }),
       }),*/
    new ol.layer.Tile({
      source: new ol.source.XYZ({
        url: basemapurl,
        projection: 'EPSG:3857'
      })
    }),
    new ol.layer.Tile({
      name: 'amc_boundary',
      cacheSize: 0,
      source: new ol.source.TileWMS({
        url: geo_url + '/wms',
        params: {
          'LAYERS': 'heritage:amc_boundary',
          'TILED': true
        }
      })
    })

  ],
  target: 'map',
  renderer:'webgl',
  view: new ol.View({
    center: ol.proj.transform([72.5833, 23.0227], 'EPSG:4326', 'EPSG:3857'),
    zoom: 14,
    maxZoom: 20,
    constrainResolution: true,
    extent: [8054494.444982627, 2609866.0596554293, 8103746.2631960325, 2663670.5669473833]
  })
});
var cql_filter = "lng='en' and default_pin =1";
var vectorSource_heritage = new ol.source.Vector({
  format: new ol.format.GeoJSON(),
  url: function (extent) {
    return geo_url + '/wfs?service=WFS&' +
      'version=2.0.0&request=GetFeature&typename=heritage:heritage&' +
      'propertyName=category_name,parent_id,child_id,category_color,title,category_type,geom&' +

      'cql_filter=' + cql_filter + '&' +
      'outputFormat=application/json&srsname=EPSG:3857&exceptions=application/json' //&bbox=' + ol.proj.transformExtent(extent,'EPSG:3857','EPSG:4326').join(',')
    ;
  }
});

var simple_feature_style = function (feature, resolution) {
  var nm;
  let src = endpointapiurl + 'getIcon/' + feature.get('parent_id') + '/' + icontype;
  nm = feature.get('parent_id');
  if (feature.get('table_name')) {
    src = geo_url + '/www/' + feature.get('table_name') + '.svg';
    nm = feature.get('table_name');
  }
  var cstyle = styleCache[nm];
  // console.log(src);
  if (!cstyle) {
    cstyle = [new ol.style.Style({

      image: new ol.style.Icon({
        anchor: [0.5, 17],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        src: src,
      }),
      text: new ol.style.Text({
        font: '12px Verdana',
        rotateWithView: true,
        overflow: true,
        scale: 1,
        offsetY: 6,
        textBaseline: 'hanging'
      })

    })];
    styleCache[nm] = cstyle;
  }
  return cstyle;
};
var selectedStyleFunction = function (feature, resolution) {

  if (feature.getProperties().title == "My Location") {
    return new ol.style.Style({
      image: new ol.style.Circle({
        radius: 7,
        fill: new ol.style.Fill({
          color: 'rgba(21,101,192,1)'
        }),
        stroke: new ol.style.Stroke({
          color: 'rgba(255,255,255,1)',
          width: 3
        })
      })
    });
  }
  let src = feature.get('parent_id') ? endpointapiurl + 'getIcon/' + feature.get('parent_id') + '/' + icontype :
    endpointapiurl + 'getIcon/' + feature.get('category_id') + '/kiosk';
  if (feature.get('table_name')) {
    src = geo_url + '/www/' + feature.get('table_name') + '.svg';
  }
  let sstyle = new ol.style.Style({

    image: new ol.style.Icon({
      anchor: [0.5, 17],
      fill: 'red',
      //padding: [1150, 150, 150, 150],
      anchorXUnits: 'fraction',
      anchorYUnits: 'pixels',
      src: src,
      scale: 1.5
    })
  });
  if (feature.get('cycleCount') >= 0) {
    let cnt = feature.get('cycleCount').toString();
    src = cnt > 0 ? './mybyk1.svg' : './mybyk0.svg';
    sstyle = new ol.style.Style({
      text: new ol.style.Text({
        //font: '12px helvetica,sans-serif',
        scale: 2,
        offsetY: -11,
        text: cnt,
        fill: new ol.style.Fill({
          color: '#000'
        }),
        stroke: new ol.style.Stroke({
          color: '#fff',
          width: 2
        })
      }),
      image: new ol.style.Icon({
        anchor: [0.5, 17],
        fill: 'red',
        //padding: [1150, 150, 150, 150],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        src: src,
        scale: 2.2
      })
    });

  }
  return [sstyle];
};
var vector_heritage = new ol.layer.Vector({
  // renderMode: 'image',
  source: vectorSource_heritage,
  name: 'explore',
  style: simple_feature_style,
  zIndex: 10
});
addMask();
map.addLayer(vector_heritage);

map.on('moveend',function(event){

	if(ispublicamenityselected && map.getView().getZoom()<14)
	{

		 container.getElementsByClassName('ol-popup-closer')[0].click();
		 	ispublicamenityselected=false;
	}
	});
map.on('click', function (evt) {
  var coordinate = evt.coordinate;
  var hdms = ol.coordinate.toStringHDMS(ol.proj.toLonLat(coordinate));


  var feature = map.forEachFeatureAtPixel(evt.pixel, function (features, layer) {
    //you can add a condition on layer to restrict the listener
    return features;
  });
  //font-family: 'Merriweather Sans';color:' + color + '
  //for public amenity
  try {
    let lyr = map.getLayers().getArray().filter(itm => {
      if (itm.get('name') == 'public_amenity') {
        return itm;
      }
    });
    if (!feature && public_amenity && lyr.length > 0 && public_amenity.getVisible()) {
      var url = public_amenity.getSource().getFeatureInfoUrl(
        evt.coordinate,
        map.getView().getResolution(),
        map.getView().getProjection(), {
          'INFO_FORMAT': 'application/json' //, 'propertyName': 'table_name,geometry'
        }
      );
      if (url) {
        fetch(url)
          .then(function (response) {
            return response.text();
          })
          .then(function (html) {
            l = JSON.parse(html);
            container.getElementsByClassName('ol-popup-closer')[0].click();
            if (l.features.length > 0) {
				ispublicamenityselected=true;

              let fid = ol.proj.transform(l.features[0].geometry.coordinates, 'EPSG:3857', 'EPSG:4326').reverse().toString();

              let src = './direction.svg';
              let color = 'red',
                title = l.features[0].properties.table_name.replace(/_/g, ' ');
              title = l.features[0].properties.landmarks + '-' + title.charAt(0).toUpperCase() + title.slice(1);
              let type = 'public';
              content.innerHTML = '<span   type="' + type + '"; style="font-family: \'Merriweather Sans\';vertical-align: top;display: inline-block;padding: 5px 0 0 5px;" data-id=' + fid + '>' + title +
                '</span><span><img  style="display: inline-block;padding: 5px 5px 0;" alt="" src="' + src + '"></span>';
              overlay.setPosition(l.features[0].geometry.coordinates);
            } //else{container.getElementsByClassName('ol-popup-closer')[0].click();}

          });
      }
    }
  } catch (err) {}

  //public amenity end
  if (feature) {
    if (feature.get('child_id')) {
			ispublicamenityselected=false;
      let title = feature.get('title');
      let fid = feature.get('child_id');
      let type = feature.get('category_type');
      let src = 'overlay_icons/Overlay Icons/1/' + feature.get('parent_id') + '.svg';
      let color = feature.get('category_color');
      /*content.innerHTML = '<span   type="' + type + '"; style="font-family: \'Merriweather Sans\';vertical-align: top;display: inline-block;padding: 5px 0 0 5px;" data-id=' + fid + '>' + title +
        '</span><span><img  style="display: inline-block;padding: 5px 5px 0;" alt="" src="' + endpointapiurl + 'getIcon/' + feature.get('parent_id') + '/icon' + '"></span>';
      overlay.setPosition(feature.getGeometry().getCoordinates());
      */
      //align="middle" hspace="15"
      let dataid = fid;
      //let type = type;

      let dataId = {
        nid: dataid,
        type: type
      };
      // For Android
      if (window.appInterface) {
        window.appInterface.getPinInfo(dataid, type);
      }

      // For iOS
      if (window.webkit && window.webkit.messageHandlers) {
        window.webkit.messageHandlers.getPinInfo.postMessage(dataId);
      }
    } else if (feature.get('table_name')) {
      let clonefeature = {
        ...feature.getProperties()
      };
      delete clonefeature.geometry;

      clonefeature.type = "trasit_point";

      if (!mylocation) {
        mylocation = [23.0131, 72.5914];
      }
      clonefeature.distance = getCoordsDistance(mylocation, [feature.get('latitude'), feature.get('longitude')]);
      // console.log(clonefeature);

      // For Android
      if (window.appInterface) {
        // window.appInterface.getPinInfo(dataid, type);
        window.appInterface.getPinInfo(JSON.stringify(clonefeature));
      }

      // For iOS
      if (window.webkit && window.webkit.messageHandlers) {
        window.webkit.messageHandlers.getPinInfo.postMessage(clonefeature);
      }

    } else if (feature.get('cycleCount') >= 0) {
      let clonefeature = {
        ...feature.getProperties()
      };

      if (!mylocation) {
        mylocation = [23.0131, 72.5914];
      }
      clonefeature.distance = getCoordsDistance(mylocation, ol.proj.toLonLat(feature.getGeometry().getCoordinates(), 'EPSG:3857').reverse());
      let pos = ol.proj.toLonLat(feature.getGeometry().getCoordinates(), 'EPSG:3857').reverse();
      clonefeature.latitude = pos[0];
      clonefeature.longitude = pos[1];

      delete clonefeature.geometry;

      clonefeature.type = "mybyk";

      // For Android
      if (window.appInterface) {
        // window.appInterface.getPinInfo(dataid, type);
        window.appInterface.getPinInfo(JSON.stringify(clonefeature));
      }

      // For iOS
      if (window.webkit && window.webkit.messageHandlers) {
        window.webkit.messageHandlers.getPinInfo.postMessage(clonefeature);
      }

    } else if (feature.get('title') == 'My Location') {} else {
      let title = feature.get('title');
      //let fid = feature.get('child_id');
      content.innerHTML = '<span>' + title + '</span>';
      overlay.setPosition(feature.getGeometry().getCoordinates());
    }
  }

});
map.on('pointermove', evt => {
  if (!evt.dragging) {
    map.getTargetElement().style.cursor = map.hasFeatureAtPixel(map.getEventPixel(evt.originalEvent)) ? 'pointer' : '';
  }
});

var locationLayer = new ol.layer.Vector({
  source: new ol.source.Vector(),
  name: 'mylocation',
  zIndex: 15,
  map: map,
  style:function (feature) {

   return new ol.style.Style({

      image: new ol.style.Icon({
        anchor: [0, 1],
        fill: 'red',
        offsetOrigin: 'top-right',
        //padding: [1150, 150, 150, 150],
        // anchorXUnits: 'fraction',
        //anchorYUnits: 'pixels',
        src: feature.getProperties().icon
      })
    })


  }
  /* style: new ol.style.Style({
    image: new ol.style.Circle({
      radius: 7,
      fill: new ol.style.Fill({
        color: 'rgba(21,101,192,1)'
      }),
      stroke: new ol.style.Stroke({
        color: 'rgba(255,255,255,1)',
        width: 3
      })
    })
  })*/
});

function setMyLocation(locationObj) {
  if (locationObj !== null && typeof locationObj !== 'object') {
    switch (typeof locationObj) {
      case 'string':
        locationObj = JSON.parse(locationObj);
        break;

    }
  }

  let lon = locationObj.longitude,
    lat = locationObj.latitude,
    icon=locationObj.icon ||'current_location.png';
  if (lat && lon) {
    locationLayer.getSource().clear();
    let coords = ol.proj.fromLonLat([lon, lat], 'EPSG:3857');
    let feature = new ol.Feature(new ol.geom.Point(coords));
    feature.setProperties({
      "title": "My Location",
      "icon":icon
    });
    locationLayer.getSource().addFeature(feature);
    /*map.getView().fit(feature.getGeometry().getExtent(), {
      size: map.getSize(),
      padding: [20, 20, 20, 20],
      maxZoom: 14
    });*/
    map.getView().setCenter(coords);
    map.getView().setZoom(14);
   // let geom = new ol.geom.Circle(coords, 5000);
   // let extent = geom.getExtent();

//    map.getView().fit(extent,map.getView().calculateExtent(map.getSize()) //{
//      size: map.getView().calculateExtent(map.getSize()),
//      zoom:15
//      //padding: [50, 50, 50, 50]
//      //maxZoom: 14
//    });

  }

}

var setLocationIcon=function (iconname){
var vsource=locationLayer.getSource();
if(vsource && vsource.getFeatures().length>0){
let _feature=vsource.getFeatures()[0];
let _prop=_feature.getProperties();
_prop.icon=iconname;
_feature.setProperties(_prop);

}

}


var select = new ol.interaction.Select({
  style: selectedStyleFunction,
  //layers: [vector_heritage, vector_kiosk],
  filter: function (layer) {
    return layer.get('title') == 'My Location' ? false : true;
  },
  toggleCondtion: function (layer) {
    return layer.get('title') == 'My Location' ? true : false;
  }
});
select.on('select', function (evt, e2) {
  if (evt.deselected.length && !evt.selected.length) {
    container.getElementsByClassName('ol-popup-closer')[0].click();
  }
});
map.addInteraction(select);

function refreshWfsLayer(layer, params) {
  //vector_heritag
  layer.getSource().clear(true);
  cql_filter = "lng='en' and default_pin =1";
  layer.cql_filter = "lng='en';category_name=Mughal era";

}

function updateHeritageWalk(categoryid) {
  let lyr = [];
  lyr = map.getLayers().getArray().filter(itm => {
    if (itm.get('name') == 'heritage_walk') {
      return itm;
    } else if (itm.get('name') == 'heritage_era') {
      map.removeLayer(itm);
    }
  });

  if (lyr.length && lyr[0].getSource() instanceof ol.source.Tile) {
    let params = lyr[0].getSource().updateParams({
      'CQL_FILTER': 'categoryid=' + categoryid
    });
  } else {
    heritageWalk = new ol.layer.Tile({
      name: 'heritage_walk',
      cacheSize: 0,
      zIndex: 9,
      source: new ol.source.TileWMS({
        url: geo_url + '/wms',
        params: {
          'LAYERS': 'heritage:heritage_walk',
          'TILED': true,
          'CQL_FILTER': 'categoryid=' + categoryid
        }
      })
    });
    map.addLayer(heritageWalk);
    heritageWalk.on('change', function (evt) {
      //console.log("map params updated");
      map.renderSync();
    })
  }

}

function updateHeritageEra(categoryid) {
  map.getLayers().getArray().filter(itm => {
    if (itm.get('name') == 'heritage_era') {
      return itm;
    } else if (itm.get('name') == 'heritage_walk') {
      map.removeLayer(itm);
    }
  });

  if (lyr.length && lyr[0].getSource() instanceof ol.source.Tile) {
    let params = lyr[0].getSource().updateParams({
      'CQL_FILTER': 'categoryid=' + categoryid
    });
  } else {
    heritageWalk = new ol.layer.Tile({
      name: 'heritage_era',
      zIndex: 9,
      cacheSize: 0,
      source: new ol.source.TileWMS({
        url: geo_url + '/wms',
        params: {
          'LAYERS': 'heritage:heritage_era',
          'TILED': true,
          'CQL_FILTER': 'categoryid=' + categoryid
        }
      })
    });
    map.addLayer(heritageWalk);
    heritageWalk.on('change', function (evt) {
      //  console.log("map params updated");
      map.renderSync();
    })
  }

}

function updateHeritageSites(filterJson) {
  addMask();
  if (filterJson !== null && typeof filterJson !== 'object') {
    switch (typeof filterJson) {
      case 'string':
        filterJson = JSON.parse(filterJson);
        break;

    }
  }
  let arr = [];

  Object.keys(filterJson).forEach(itm => {
    if (itm == "parent_id" || itm == "child_id") {
      arr.push(itm + " in (" + filterJson[itm] + ") ");
    } else {
      arr.push(itm + "='" + filterJson[itm] + "'");
    }
  });
  //  Object.keys(filterJson).forEach(itm => arr.push(itm + "='" + filterJson[itm] + "'"));
  let cql_filter = arr.join(' and ');
  //"lng='hi'and category_name='Historic monuments'";
  let url = geo_url + '/wfs?service=WFS&' +
    'version=2.0.0&request=GetFeature&typename=heritage:heritage&' +
    'propertyName=category_name,parent_id,child_id,category_color,title,category_type,geom&' +
    'cql_filter=' + cql_filter + '&' +
    'outputFormat=application/json&srsname=EPSG:3857&exceptions=application/json';
  var xhr = new XMLHttpRequest();
  xhr.open('GET', url);
  xhr.onload = function () {
    if (xhr.status == 200) {
      try {
        vector_kiosk.setVisible(false);
      } catch (err) {}
      vector_heritage.setVisible(true);
      vectorSource_heritage.clear();
      if (JSON.parse(xhr.responseText).features.length) {
        vectorSource_heritage.addFeatures(
          vectorSource_heritage.getFormat().readFeatures(xhr.responseText));
      } else {
       // alert("No Site available.");
      }

    }
  }
  xhr.send();
}
vectorSource_heritage.on('addfeature', function (evt) {
  // console.log("pin addfeature");
  if (vectorSource_heritage.getFeatures().length) {
    container.getElementsByClassName('ol-popup-closer')[0].click();
    map.getView().fit(vectorSource_heritage.getExtent(), {
      size: map.getSize(),
      padding: [20, 20, 20, 20],
      maxZoom: 17
    });
  }
});
vectorSource_heritage.on('change', function (a, b, c) {
  //console.log("pin change");
  removeMask();
});
vector_heritage.on('change', function (a, b, c) {
  //	console.log("pin layer change");
});

function addPublicAmenity() {
  let arr = ['z']; //['fire_stations', 'hospitals', 'uhc', 'medical_clinic', 'hotel', 'police_station', 'restaurant'];

  public_amenity = new ol.layer.Tile({
    name: 'public_amenity',
    cacheSize: 0,
    source: new ol.source.TileWMS({
      url: geo_url + '/wms',
      params: {
        'LAYERS': 'heritage:public_amenity',
        'TILED': true,
        'CQL_FILTER': "table_name in('" + arr.join("','") + "')"
      },
      zIndex: 5
    })
  });
  map.addLayer(public_amenity);
}

function removePublicAmenity() {

  map.getLayers().getArray().filter(itm => {
    if (itm.get('name') == 'public_amenity') {
      map.removeLayer(itm);
	  		 container.getElementsByClassName('ol-popup-closer')[0].click();
	  		 	ispublicamenityselected=false;

    }
  });
  container.getElementsByClassName('ol-popup-closer')[0].click();
}

//transit points
function addTransitPoint() {
  let arr = ['z']; //['amts_stop', 'brts_stop', 'bus_depot', 'railway_station', 'gsrtc_depots','bus_terminus'];

  let cql_filter = "table_name in('" + arr.join("','") + "')";

  vectorSource_transit = new ol.source.Vector({
    format: new ol.format.GeoJSON(),
    url: function (extent) {
      return geo_url + '/wfs?service=WFS&' +
        'version=2.0.0&request=GetFeature&typename=heritage:public_amenity&' +
        'propertyName=gid,objectid,landmarks,ward,zone,table_name,longitude,latitude,geom&' +
        'cql_filter=' + cql_filter + '&' +
        'outputFormat=application/json&srsname=EPSG:3857&exceptions=application/json' //&bbox=' + ol.proj.transformExtent(extent,'EPSG:3857','EPSG:4326').join(',')
      ;
    }
  });
  transit_vector = new ol.layer.Vector({
    renderMode: 'image',
    source: vectorSource_transit,
    name: 'public_transit',
    style: simple_feature_style,
    zIndex: 9,
    minZoom: 13
  });

  map.addLayer(transit_vector);
  addMybyk();
}

function removeTransitpoint() {
  map.getLayers().getArray().filter(itm => {
    if (itm.get('name') == 'public_transit' || 'mybyk' == itm.get('name')) {
      map.removeLayer(itm)
    }
  });
  map.getLayers().getArray().filter(itm => {
    if ('mybyk' == itm.get('name')) {
      map.removeLayer(itm)
    }
  });
  container.getElementsByClassName('ol-popup-closer')[0].click();
}

//KIOSK
function addKiosk(filterJson) {
  try {
    map.removeLayer(vector_kiosk);

  } catch (error) {}
  var simple_feature_style_kiosk = function (feature, resolution) {
    var nm;
    let src = endpointapiurl + 'getIcon/' + feature.get('category_id') + '/kiosk';
     nm = feature.get('category_id');
    //console.log(src);
    var cstyle = styleCache[nm];
     if (!cstyle) {
    cstyle= [new ol.style.Style({

      image: new ol.style.Icon({
        anchor: [0.5, 17],
        //padding: [1150, 150, 150, 150],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        src: src,
      }),

      text: new ol.style.Text({
        font: '12px Verdana',
        rotateWithView: true,
        overflow: true,
        scale: 1,
        offsetY: 6,
        textBaseline: 'hanging'

      })

    })];
      styleCache[nm] = cstyle;
  }
   return cstyle;
  };

  let cql_filter = "lng='en' and default_pin =1";
  if (filterJson !== null && typeof filterJson !== 'object') {
    switch (typeof filterJson) {
      case 'string':
        filterJson = JSON.parse(filterJson);
        break;

    }
  }
  let arr = [];

  Object.keys(filterJson).forEach(itm => {
    if (itm == "category_id") {
      arr.push(itm + " in (" + filterJson[itm] + ") ");
    } else {
      arr.push(itm + "='" + filterJson[itm] + "'");
    }
  });
  if (arr.length)
    cql_filter = arr.join(' and ');
  vectorSource_kiosk = new ol.source.Vector({
    format: new ol.format.GeoJSON(),
    type: 'kiosk',
    url: function (extent) {
      return geo_url + '/wfs?service=WFS&' +
        'version=2.0.0&request=GetFeature&typename=heritage:kiosk&' +
        'propertyName=title,geom,category_name,category_id,category_color,lng,child_id&' +
        'cql_filter=' + cql_filter + '&' +
        'outputFormat=application/json&srsname=EPSG:3857&exceptions=application/json' //+'bbox=' + extent.join(',')
      ;
    }
  });

  vector_kiosk = new ol.layer.Vector({
    source: vectorSource_kiosk,
    name: 'intrests',
    style: simple_feature_style_kiosk,
    zIndex: 10
  });

  map.addLayer(vector_kiosk);
  vectorSource_kiosk.once('change', function (evt) {
    if (vectorSource_kiosk.getState() === 'ready') {
      if (vectorSource_kiosk.getFeatures().length) {
        container.getElementsByClassName('ol-popup-closer')[0].click();
        map.getView().fit(vectorSource_kiosk.getExtent(), {
          size: map.getSize(),
          padding: [20, 20, 20, 20],
          maxZoom: 17
        });
      }
    }
  });
  vector_kiosk.setVisible(true);
  vector_heritage.setVisible(false);
}

function updateIcontype(type) {
  if (type)
    icontype = type;
}

function addMask() {
  /*document.getElementById("mask").style.display = "block";
  // For Android
  if (window.appInterface) {
    window.appInterface.addMask();
  }

  // For iOS
  if (window.webkit && window.webkit.messageHandlers) {
    window.webkit.messageHandlers.addMask.postMessage();
  }*/
}

function removeMask() {
  /*document.getElementById("mask").style.display = "none";
  // For Android
  if (window.appInterface) {
    window.appInterface.removeMask();
  }

  // For iOS
  if (window.webkit && window.webkit.messageHandlers) {
    window.webkit.messageHandlers.removeMask.postMessage();
  }*/
}

function deselectSite() {
  try {
    select.getFeatures().clear();
  } catch (err) {}
}

function addMybyk() {
  var geojson = {
    type: "FeatureCollection",
    features: [],
  };
  fetch("https://dashboard.greenpedia.in/third-party/ws_map_response_data.php", {
      method: 'get',
      headers: {
        "Parameter": "",
        "bearer": "THEHYFTNYBGPHIHEKDHAMGT66AFQBX89TDGN5LMB",
        "Content-Type": "application/json"

      }
    }).then(response => response.json())
    .then(function (data) {
      let d = data;
      for (let i = 0; i < d.allTerminals.length; i++) {

        geojson.features.push({
          "type": "Feature",
          "geometry": {
            "type": "Point",
            "coordinates": ol.proj.transform([Number(d.allTerminals[i].terminalLong), Number(d.allTerminals[i].terminalLat)], 'EPSG:4326', 'EPSG:3857')
          },
          "properties": {
            "id": d.allTerminals[i].cycleCount,
            "stationName": d.allTerminals[i].stationName,
            "allCycleCount": d.allTerminals[i].allCycleCount,
            "cycleCount": d.allTerminals[i].cycleCount
          }
        });
      } //adding mybyk layer

      vectorSource_mybyk = new ol.source.Vector({
        features: new ol.format.GeoJSON().readFeatures(geojson)
      });

      vector_mybyk = new ol.layer.Vector({
        source: vectorSource_mybyk,
        name: 'mybyk',
        style: allbusstyle,
        zIndex: 9,
        visible: false
      });
      map.addLayer(vector_mybyk);
      //end
      console.log('Request succeeded with JSON response', data);
    })
    .catch(function (error) {
      console.log('Request failed', error);
    });
  //console.log(geojson);

}

function getCoordsDistance(latlng1, latlng2) {

  var markers = [];

  markers.push(ol.proj.transform(latlng1, 'EPSG:4326', map.getView().getProjection()));
  markers.push(ol.proj.transform(latlng2, 'EPSG:4326', map.getView().getProjection()));

  var line = new ol.geom.LineString(markers, 'XY');
  var length = Math.round(line.getLength() * 100) / 100;

  if (length >= 1000) {
    length = (Math.round(length / 1000 * 100) / 100) +
      ' ' + 'km';
  } else {
    length = Math.round(length) +
      ' ' + 'm';
  }
  return length;
}

function updatePublicAmenity(selectedamenitynames, type, show) {
  if(!show){
  		if(ispublicamenityselected || map.getView().getZoom()<14)
  			{

  				 container.getElementsByClassName('ol-popup-closer')[0].click();
  				 	ispublicamenityselected=false;
  	}
  		}
  switch (type) {
    case 'public_amenity':
      if (!Array.isArray(selectedamenitynames)) {
        if (show) {
          selected_publicamenity.push(selectedamenitynames);
          selectedamenitynames = selected_publicamenity;
        } else {
          filteredItems = selected_publicamenity.filter(function (item) {
            return item !== selectedamenitynames
          });
          selected_publicamenity = selectedamenitynames = filteredItems;

        }
        //selectedamenitynames = selectedamenitynames.split(",");
        //selectedamenitynames = selectedamenitynames.join("','");
      }
      if ((!selectedamenitynames) || selectedamenitynames.length == 0) {
        selectedamenitynames = ['z']; //['fire_stations', 'hospitals', 'uhc', 'medical_clinic', 'hotel', 'police_station', 'restaurant'].join("','");
      }
      map.getLayers().getArray().filter(itm => {
        if (itm.get('name') == 'public_amenity') {
          let params = itm.getSource().getParams();
          params.CQL_FILTER = "table_name in('" + selectedamenitynames.join("','") + "')";
          itm.getSource().updateParams(params);

        }
      });
      break;
    case 'public_transit':
      if (!Array.isArray(selectedamenitynames)) {
        if (show) {
          selected_publictransit.push(selectedamenitynames);
          selectedamenitynames = selected_publictransit;
        } else {
          filteredItems = selected_publictransit.filter(function (item) {
            return item !== selectedamenitynames
          });
          selected_publictransit = selectedamenitynames = filteredItems;
        }
        //selectedamenitynames = selectedamenitynames.split(",");
        //selectedamenitynames = selectedamenitynames.join("','");
      }
      if ((!selectedamenitynames) || selectedamenitynames.length == 0) {
        selectedamenitynames = ['z']; //['amts_stop', 'brts_stop', 'bus_depot', 'railway_station', 'gsrtc_depots','bus_terminus'].join("','");
      }


      let cql_filter = "table_name in('" + selectedamenitynames.join("','") + "')";
      let url = geo_url + '/wfs?service=WFS&' +
        'version=2.0.0&request=GetFeature&typename=heritage:public_amenity&' +
        'propertyName=gid,objectid,landmarks,ward,zone,table_name,longitude,latitude,geom&' +
        'cql_filter=' + cql_filter + '&' +
        'outputFormat=application/json&srsname=EPSG:3857&exceptions=application/json';
      var xhr = new XMLHttpRequest();
      xhr.open('GET', url);
      xhr.onload = function () {
        if (xhr.status == 200) {
          try {
            vector_kiosk.setVisible(false);
          } catch (err) {}
          transit_vector.setVisible(true);
          vectorSource_transit.clear();
          if (JSON.parse(xhr.responseText).features.length) {
            vectorSource_transit.addFeatures(
              vectorSource_transit.getFormat().readFeatures(xhr.responseText));
          } else {
           // alert("No Site available.");
          }

        }
      }
      xhr.send();
      break;
    case 'mybyk':
      map.getLayers().getArray().filter(itm => {
        if (itm.get('name') == 'mybyk') {
          itm.setVisible(show);
        }
      });

      break;
  }



}